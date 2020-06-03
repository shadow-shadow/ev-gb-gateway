package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.common.util.ByteUtil;
import com.dyy.tsp.evgb.gateway.protocol.common.Constants;
import com.dyy.tsp.netty.common.IStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

@Data
@SuppressWarnings("all")
public class TerminalControlType implements IStatus {

    @ApiModelProperty(value = "指令请求ID",example = "1")
    private Short id;

    @ApiModelProperty(value = "拨号点名称",example = "拨号点名称")
    private String dialPointName;

    @ApiModelProperty(value = "拨号用户名",example = "拨号用户名")
    private String dialUserName;

    @ApiModelProperty(value = "拨号密码",example = "拨号密码")
    private String dialPassword;

    @ApiModelProperty(value = "地址",example = "127.0.0.1")
    private String host;

    @ApiModelProperty(value = "端口",example = "8111")
    private Integer port;

    @ApiModelProperty(value = "车载终端制造商ID",example = "1234")
    private String manufacturersId;

    @ApiModelProperty(value = "硬件版本", example = "12345")
    private String hardwareVersion;

    @ApiModelProperty(value = "固件版本", example = "12345")
    private String softwareVersion;

    @ApiModelProperty(value = "升级URL地址", example = "www.baidu.com")
    private String upgradeURL;

    @ApiModelProperty(value = "连接到升级服务地址时限", example = "10")
    private Integer upgradeConnectServerTimeOut;

    @ApiModelProperty(value = "报警等级", example = "1")
    private Short warnLevel;

    @ApiModelProperty(value = "报警信息", example = "01010101")
    private String warnHexString;

    @JSONField(serialize = false)
    @JsonIgnore
    private static byte separator = 0x3B;

    @Override
    public TerminalControlType decode(ByteBuf byteBuf) {
        TerminalControlType terminalControlType = new TerminalControlType();
        terminalControlType.setId(byteBuf.readUnsignedByte());
        switch (terminalControlType.getId()){
            case 1 : {
                byte[] upgradeInfoArray = new byte[byteBuf.writerIndex()-byteBuf.readerIndex()];
                byteBuf.readBytes(upgradeInfoArray);
                String upgradeInfoHex = ByteUtil.byteToHex(upgradeInfoArray);
                List<String> values = Arrays.asList(upgradeInfoHex.split("3B"));
                if(StringUtils.isNotBlank(values.get(0))){
                    terminalControlType.setDialPointName(new String(ByteUtil.hexStringToBytes(values.get(0))));
                }
                if(StringUtils.isNotBlank(values.get(1))){
                    terminalControlType.setDialUserName(new String(ByteUtil.hexStringToBytes(values.get(1))));
                }
                if(StringUtils.isNotBlank(values.get(2))){
                    terminalControlType.setDialPassword(new String(ByteUtil.hexStringToBytes(values.get(2))));
                }
                if(StringUtils.isNotBlank(values.get(3))){
                    byte[] bytes = ByteUtil.hexStringToBytes(values.get(3));
                    StringBuilder sb = new StringBuilder();
                    int a = bytes[2]&0xFF;
                    int b = bytes[3]&0xFF;
                    int c = bytes[4]&0xFF;
                    int d = bytes[5]&0xFF;
                    sb.append(a);
                    sb.append(".");
                    sb.append(b);
                    sb.append(".");
                    sb.append(c);
                    sb.append(".");
                    sb.append(d);
                    terminalControlType.setHost(sb.toString());
                }
                if(StringUtils.isNotBlank(values.get(4))){
                    terminalControlType.setPort(Integer.parseInt(values.get(4),16));
                }
                if(StringUtils.isNotBlank(values.get(5))){
                    terminalControlType.setManufacturersId(new String(ByteUtil.hexStringToBytes(values.get(5))));
                }
                if(StringUtils.isNotBlank(values.get(6))){
                    terminalControlType.setHardwareVersion(new String(ByteUtil.hexStringToBytes(values.get(6))));
                }
                if(StringUtils.isNotBlank(values.get(7))){
                    terminalControlType.setSoftwareVersion(new String(ByteUtil.hexStringToBytes(values.get(7))));
                }
                if(StringUtils.isNotBlank(values.get(8))){
                    terminalControlType.setUpgradeURL(new String(ByteUtil.hexStringToBytes(values.get(8))));
                }
                if(StringUtils.isNotBlank(values.get(9))){
                    terminalControlType.setUpgradeConnectServerTimeOut(Integer.parseInt(values.get(4),16));
                }
                break;
            }
            case 6 : {
                terminalControlType.setWarnLevel(byteBuf.readUnsignedByte());
                byte[] warnInfoArray = new byte[byteBuf.writerIndex()-byteBuf.readerIndex()];
                byteBuf.readBytes(warnInfoArray);
                terminalControlType.setWarnHexString(ByteUtil.byteToHex(warnInfoArray));
                break;
            }
        }
        return terminalControlType;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(id);
        switch (id){
            case 1:
                //拨号点名称
                if(StringUtils.isNotBlank(dialPointName)){
                    buffer.writeBytes(dialPointName.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //拨号用户名
                if(StringUtils.isNotBlank(dialUserName)){
                    buffer.writeBytes(dialUserName.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //拨号密码
                if(StringUtils.isNotBlank(dialPassword)){
                    buffer.writeBytes(dialPassword.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //地址
                if(StringUtils.isNotBlank(host)){
                    List<String> values = Arrays.asList(host.split("\\."));
                    if(values.size()!=4){
                        throw new BusinessException("TerminalControlType ip format error");
                    }
                    buffer.writeByte(0);
                    buffer.writeByte(0);
                    buffer.writeByte(Short.valueOf(values.get(0)));
                    buffer.writeByte(Short.valueOf(values.get(1)));
                    buffer.writeByte(Short.valueOf(values.get(2)));
                    buffer.writeByte(Short.valueOf(values.get(3)));
                }
                buffer.writeByte(separator);
                //端口
                if(port!=null){
                    buffer.writeShort(port);
                }
                buffer.writeByte(separator);
                //制造商ID
                if(StringUtils.isNotBlank(manufacturersId)){
                    buffer.writeBytes(manufacturersId.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //硬件版本
                if(StringUtils.isNotBlank(hardwareVersion)){
                    buffer.writeBytes(hardwareVersion.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //固件版本
                if(StringUtils.isNotBlank(softwareVersion)){
                    buffer.writeBytes(softwareVersion.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //升级URL地址
                if(StringUtils.isNotBlank(upgradeURL)){
                    buffer.writeBytes(upgradeURL.getBytes(Constants.UTF_8));
                }
                buffer.writeByte(separator);
                //连接到升级服务地址时限
                if(upgradeConnectServerTimeOut!=null){
                    buffer.writeShort(upgradeConnectServerTimeOut);
                }
                break;
            case 6:
                buffer.writeByte(warnLevel);
                buffer.writeBytes(ByteUtil.hexStringToBytes(warnHexString));
                break;
            case 0:{
                throw new BusinessException("Unused TerminalControlType id : " + id);
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
                break;
            default:
                throw new BusinessException("UnKnown TerminalControlType id : " + id);
        }
        return buffer;
    }

}
