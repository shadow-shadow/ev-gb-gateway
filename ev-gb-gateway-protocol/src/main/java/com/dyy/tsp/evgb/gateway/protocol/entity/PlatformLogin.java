package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.evgb.gateway.protocol.common.Constants;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.EncryptionType;
import com.dyy.tsp.netty.common.IStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 平台登入
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class PlatformLogin implements IStatus {

    @JSONField(serialize = false)
    @JsonIgnore
    private static final BeanTime producer = new BeanTime();

    @ApiModelProperty(value = "平台登入时间")
    private BeanTime beanTime;

    @ApiModelProperty(value = "平台登入流水号",example = "1")
    private Integer serialNum;

    @ApiModelProperty(value = "平台登入用户名",example = "PLATFORM_VIN")
    private String userName;

    @ApiModelProperty(value = "平台登入密码",example = "PLATFORM_PASSWORD123")
    private String password;

    @ApiModelProperty(value = "加密方式",example = "NONE")
    private EncryptionType encryptionType;

    @Override
    public PlatformLogin decode(ByteBuf byteBuf) throws BusinessException {
        PlatformLogin platformLogin = new PlatformLogin();
        BeanTime beanTime = producer.decode(byteBuf);
        platformLogin.setBeanTime(beanTime);
        platformLogin.setSerialNum(byteBuf.readUnsignedShort());
        platformLogin.setUserName(byteBuf.readSlice(12).toString(Constants.UTF_8));
        platformLogin.setPassword(byteBuf.readSlice(20).toString(Constants.UTF_8));
        platformLogin.setEncryptionType(EncryptionType.valuesOf(byteBuf.readUnsignedByte()));
        return platformLogin;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        if(userName.length()!=12){
            throw new BusinessException("userName length must be 12");
        }
        if(password.length()!=20){
            throw new BusinessException("password length must be 20");
        }
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeShort(serialNum);
        buffer.writeBytes(userName.getBytes(Constants.UTF_8));
        buffer.writeBytes(password.getBytes(Constants.UTF_8));
        buffer.writeByte(encryptionType.getId());
        return buffer;
    }
}
