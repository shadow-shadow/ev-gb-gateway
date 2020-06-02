package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 驱动电机数据
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class DriveMotorData implements IStatus {

    @ApiModelProperty(value = "序号",example = "1")
    private Short num;

    @ApiModelProperty(value = "驱动电机状态",example = "1")
    private Short status;

    @ApiModelProperty(value = "驱动电机控制器温度",example = "100")
    private Short controllerTemperature;

    @ApiModelProperty(value = "驱动电机转速",example = "10000")
    private Integer speed;

    @ApiModelProperty(value = "驱动电机转矩",example = "20000")
    private Integer torque;

    @ApiModelProperty(value = "驱动电机温度",example = "100")
    private Short temperature;

    @ApiModelProperty(value = "驱动电机控制器输入电压",example = "10000")
    private Integer controllerInputVoltage;

    @ApiModelProperty(value = "驱动电机控制器直流母线电流",example = "20000")
    private Integer controllerDcBusbarCurrent;

    @Override
    public DriveMotorData decode(ByteBuf byteBuf) throws BusinessException {
        DriveMotorData driveMotorData = new DriveMotorData();
        driveMotorData.setNum(byteBuf.readUnsignedByte());
        driveMotorData.setStatus(byteBuf.readUnsignedByte());
        driveMotorData.setControllerTemperature(byteBuf.readUnsignedByte());
        driveMotorData.setSpeed(byteBuf.readUnsignedShort());
        driveMotorData.setTorque(byteBuf.readUnsignedShort());
        driveMotorData.setTemperature(byteBuf.readUnsignedByte());
        driveMotorData.setControllerInputVoltage(byteBuf.readUnsignedShort());
        driveMotorData.setControllerDcBusbarCurrent(byteBuf.readUnsignedShort());
        return driveMotorData;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(num);
        buffer.writeByte(status);
        buffer.writeByte(controllerTemperature);
        buffer.writeShort(speed);
        buffer.writeShort(torque);
        buffer.writeByte(temperature);
        buffer.writeShort(controllerInputVoltage);
        buffer.writeShort(controllerDcBusbarCurrent);
        return buffer;
    }

}
