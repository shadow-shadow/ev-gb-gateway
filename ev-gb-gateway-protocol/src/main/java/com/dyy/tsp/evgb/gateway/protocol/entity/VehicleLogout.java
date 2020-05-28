package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 车辆登出
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class VehicleLogout implements IStatus {

    @JSONField(serialize = false)
    @JsonIgnore
    private static final BeanTime producer = new BeanTime();

    @ApiModelProperty(value = "车辆登出时间")
    private BeanTime beanTime;

    @ApiModelProperty(value = "车辆登出流水号",example = "1")
    private Integer serialNum;

    @Override
    public VehicleLogout decode(ByteBuf byteBuf) throws BusinessException {
        VehicleLogout vehicleLogout = new VehicleLogout();
        BeanTime beanTime = producer.decode(byteBuf);
        vehicleLogout.setBeanTime(beanTime);
        vehicleLogout.setSerialNum(byteBuf.readUnsignedShort());
        return vehicleLogout;
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
