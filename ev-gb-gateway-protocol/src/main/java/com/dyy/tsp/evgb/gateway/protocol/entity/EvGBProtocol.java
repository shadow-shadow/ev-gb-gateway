package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.common.util.ByteUtil;
import com.dyy.tsp.evgb.gateway.protocol.common.Constants;
import com.dyy.tsp.evgb.gateway.protocol.dto.VehicleCache;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.EncryptionType;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import com.dyy.tsp.netty.common.IProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.Instant;

/**
 * GB32960协议封装
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class EvGBProtocol implements IProtocol {

    @ApiModelProperty(value = "固定##开头")
    @JSONField(serialize = false)
    private Boolean begin = Boolean.TRUE;

    @ApiModelProperty(value = "命令标识",example = "HEARTBEAT")
    private CommandType commandType;

    @ApiModelProperty(value = "应答标志",example = "COMMAND")
    private ResponseType responseType = ResponseType.COMMAND;

    @ApiModelProperty(value = "车架号",example = "LSFGHHH0123456789")
    private String vin;

    @ApiModelProperty(value = "数据单元加密方式",example = "NONE")
    private EncryptionType encryptionType = EncryptionType.NONE;

    @ApiModelProperty(value = "数据单元长度",example = "0")
    private Integer length;

    @ApiModelProperty(value = "数据单元")
    private DataBody body;

    @ApiModelProperty(value = "BCC校验")
    private Boolean bcc = Boolean.TRUE;

    @ApiModelProperty(value = "十六进制原始报文")
    private String hex;

    @ApiModelProperty(value = "主要是为了防止Dispatcher重复操作Redis  协议内部不使用")
    private VehicleCache vehicleCache;

    @ApiModelProperty(value = "网关接收时间  协议内部不使用")
    private Long gatewayReceiveTime;

    @ApiModelProperty(value = "网关转发时间  协议内部不使用")
    private Long gatewayForwardTime;

    /**
     * 协议编码
     * @return
     */
    @Override
    public ByteBuf encode() throws BusinessException {
        if(vin.length()!=17){
            throw new BusinessException("vin length must be 17");
        }
        ByteBuf bccBuffer = PooledByteBufAllocator.DEFAULT.buffer();
        bccBuffer.order(ByteOrder.BIG_ENDIAN);
        bccBuffer.writeByte(commandType.getId());
        bccBuffer.writeByte(responseType.getId());
        bccBuffer.writeBytes(vin.getBytes(Charset.forName(Constants.UTF_8)));
        bccBuffer.writeByte(encryptionType.getId());
        if(body!=null){
            ByteBuf byteBuf = body.getByteBuf();
            if(byteBuf!=null){
                int i = byteBuf.readableBytes();
                if(i>0){
                    bccBuffer.writeShort(i);
                    bccBuffer.writeBytes(byteBuf);
                }
            }
        }else{
            bccBuffer.writeShort(0);
        }
        byte bcc = signBcc(bccBuffer);
        //组装数据包返回最终编码结果
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(Constants.BEGIN.getBytes(Charset.forName(Constants.UTF_8)));
        buffer.writeBytes(bccBuffer);
        buffer.writeByte(bcc);
        return buffer;
    }

    /**
     * 协议解码 只负责解码数据包
     * 数据单元解码由ParseHandler完成
     * @return
     */
    @Override
    public EvGBProtocol decode(ByteBuf byteBuf) throws BusinessException{
        EvGBProtocol protocol = new EvGBProtocol();
        byteBuf.markReaderIndex();
        byte[] hexArray = new byte[byteBuf.writerIndex()];
        byteBuf.readBytes(hexArray);
        protocol.setHex(ByteUtil.byteToHex(hexArray));
        byteBuf.resetReaderIndex();
        Boolean checkBcc = EvGBProtocol.checkBcc(byteBuf);
        protocol.setBcc(checkBcc);
        Boolean checkBegin = Constants.BEGIN.equals(byteBuf.readSlice(2).toString(Charset.forName(Constants.UTF_8)));
        protocol.setBegin(checkBegin);
        protocol.setCommandType(CommandType.valuesOf(byteBuf.readUnsignedByte()));
        protocol.setResponseType(ResponseType.valuesOf(byteBuf.readUnsignedByte()));
        protocol.setVin(byteBuf.readSlice(17).toString(Charset.forName(Constants.UTF_8)));
        protocol.setEncryptionType(EncryptionType.valuesOf(byteBuf.readUnsignedByte()));
        //校验码正确与起始符号正确的时候才解析数据单元
        if(checkBcc && checkBegin){
            int length = byteBuf.readUnsignedShort();
            protocol.setLength(length);
            if(length>0){
                protocol.setBody(new DataBody(byteBuf.readSlice(length)));
            }
        }
        protocol.setGatewayReceiveTime(Instant.now().toEpochMilli());
        byteBuf.readUnsignedByte();
        return protocol;
    }

    /**
     * BCC校验(异或校验)
     * @param byteBuf
     * @return
     */
    public static byte signBcc(ByteBuf byteBuf) {
        byte cs = 0;
        while (byteBuf.isReadable()){
            cs ^= byteBuf.readByte();
        }
        byteBuf.resetReaderIndex();
        return cs;
    }

    /**
     * 校验BCC是否正确
     * @param byteBuf
     * @return
     */
    public static Boolean checkBcc(ByteBuf byteBuf) {
        byte bcc = byteBuf.getByte(byteBuf.readableBytes()-1);
        ByteBuf slice = byteBuf.slice(2, byteBuf.readableBytes()-3);
        byte checkBcc = signBcc(slice);
        byteBuf.resetReaderIndex();
        return bcc == checkBcc;
    }
}
