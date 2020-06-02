package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 发动机数据
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class EngineData implements IStatus {

    @ApiModelProperty(value = "发动机状态",example = "1")
    private Short status;

    @ApiModelProperty(value = "曲轴转速",example = "10000")
    private Integer crankshaftSpeed;

    @ApiModelProperty(value = "燃料消耗率",example = "10000")
    private Integer fuelConsumption;

    @Override
    public EngineData decode(ByteBuf byteBuf) throws BusinessException {
        EngineData engineData = new EngineData();
        engineData.setStatus(byteBuf.readUnsignedByte());
        engineData.setCrankshaftSpeed(byteBuf.readUnsignedShort());
        engineData.setFuelConsumption(byteBuf.readUnsignedShort());
        return engineData;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(status);
        buffer.writeShort(crankshaftSpeed);
        buffer.writeShort(fuelConsumption);
        return buffer;
    }

}
