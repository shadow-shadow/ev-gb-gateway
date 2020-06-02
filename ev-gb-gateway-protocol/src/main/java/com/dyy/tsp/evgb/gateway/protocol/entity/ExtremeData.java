package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 极值数据
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class ExtremeData implements IStatus {

    @ApiModelProperty(value = "最高电压电池子系统号",example = "1")
    private Short maxVoltageSystemNum;

    @ApiModelProperty(value = "最高电压单体电池代号",example = "1")
    private Short maxVoltagebatteryNum;

    @ApiModelProperty(value = "单体电池电压最高值",example = "20000")
    private Integer batteryMaxVoltage;

    @ApiModelProperty(value = "最低电压电池子系统号",example = "2")
    private Short minVoltageSystemNum;

    @ApiModelProperty(value = "最低电压单体电池代号",example = "2")
    private Short minVoltagebatteryNum;

    @ApiModelProperty(value = "单体电池电压最低值",example = "10000")
    private Integer batteryMinVoltage;

    @ApiModelProperty(value = "最高温度子系统号",example = "1")
    private Short maxTemperatureSystemNum;

    @ApiModelProperty(value = "最高温度探针序号",example = "1")
    private Short maxTemperatureNum;

    @ApiModelProperty(value = "最高温度值",example = "100")
    private Short maxTemperature;

    @ApiModelProperty(value = "最低温度子系统号",example = "2")
    private Short minTemperatureSystemNum;

    @ApiModelProperty(value = "最低温度探针序号",example = "2")
    private Short minTemperatureNum;

    @ApiModelProperty(value = "最低温度值",example = "80")
    private Short minTemperature;

    @Override
    public ExtremeData decode(ByteBuf byteBuf) throws BusinessException {
        ExtremeData extremeData = new ExtremeData();
        extremeData.setMaxVoltageSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMaxVoltagebatteryNum(byteBuf.readUnsignedByte());
        extremeData.setBatteryMaxVoltage(byteBuf.readUnsignedShort());
        extremeData.setMinVoltageSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMinVoltagebatteryNum(byteBuf.readUnsignedByte());
        extremeData.setBatteryMinVoltage(byteBuf.readUnsignedShort());
        extremeData.setMaxTemperatureSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMaxTemperatureNum(byteBuf.readUnsignedByte());
        extremeData.setMaxTemperature(byteBuf.readUnsignedByte());
        extremeData.setMinTemperatureSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMinTemperatureNum(byteBuf.readUnsignedByte());
        extremeData.setMinTemperature(byteBuf.readUnsignedByte());
        return extremeData;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(maxVoltageSystemNum);
        buffer.writeByte(maxVoltagebatteryNum);
        buffer.writeShort(batteryMaxVoltage);
        buffer.writeByte(minVoltageSystemNum);
        buffer.writeByte(minVoltagebatteryNum);
        buffer.writeShort(batteryMinVoltage);
        buffer.writeByte(maxTemperatureSystemNum);
        buffer.writeByte(maxTemperatureNum);
        buffer.writeByte(maxTemperature);
        buffer.writeByte(minTemperatureSystemNum);
        buffer.writeByte(minTemperatureNum);
        buffer.writeByte(minTemperature);
        return buffer;
    }

}
