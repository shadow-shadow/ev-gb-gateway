package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.nio.ByteOrder;

/**
 * 平台登出
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class PlatformLogout implements IStatus {

    private static final BeanTime producer = new BeanTime();

    @ApiModelProperty(value = "平台登出时间")
    private BeanTime beanTime;

    @ApiModelProperty(value = "平台登出流水号",example = "1")
    private Integer serialNum;

    @Override
    public PlatformLogout decode(ByteBuf byteBuf) throws BusinessException {
        PlatformLogout platformLogout = new PlatformLogout();
        BeanTime beanTime = producer.decode(byteBuf);
        platformLogout.setBeanTime(beanTime);
        platformLogout.setSerialNum(byteBuf.readUnsignedShort());
        return platformLogout;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeShort(serialNum);
        return buffer;
    }
}
