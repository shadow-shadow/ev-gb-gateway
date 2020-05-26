package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 整车数据
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class VehicleData implements IStatus {

    @ApiModelProperty(value = "车辆状态",example = "1")
    private Short vehicleStatus;

    @ApiModelProperty(value = "充电状态",example = "1")
    private Short chargeStatus;

    @ApiModelProperty(value = "运行模式",example = "1")
    private Short operationMode;

    @ApiModelProperty(value = "车速",example = "1100")
    private Integer speed;

    @ApiModelProperty(value = "累计里程",example = "111100")
    private Long mileage;

    @ApiModelProperty(value = "总电压",example = "10000")
    private Integer totalVoltage;

    @ApiModelProperty(value = "总电流",example = "20000")
    private Integer totalCurrent;

    @ApiModelProperty(value = "SOC电量",example = "80")
    private Short soc;

    @ApiModelProperty(value = "DC_DC状态",example = "1")
    private Short dcStatus;

    @ApiModelProperty(value = "挡位",example = "1")
    private Short gears;

    @ApiModelProperty(value = "绝缘电阻",example = "1000")
    private Integer insulationResistance;

    @ApiModelProperty(value = "加速行程值",example = "100")
    private Short accelerationValue;

    @ApiModelProperty(value = "制动踏板状态",example = "101")
    private Short brakePedalCondition;

    @Override
    public VehicleData decode(ByteBuf byteBuf) throws BusinessException {
        VehicleData vehicleData = new VehicleData();
        //车辆状态
        vehicleData.setVehicleStatus(byteBuf.readUnsignedByte());
        //充电状态
        vehicleData.setChargeStatus(byteBuf.readUnsignedByte());
        //运行模式
        vehicleData.setOperationMode(byteBuf.readUnsignedByte());
        //车速
        vehicleData.setSpeed(byteBuf.readUnsignedShort());
        //累计里程
        vehicleData.setMileage(byteBuf.readUnsignedInt());
        //总电压
        vehicleData.setTotalVoltage(byteBuf.readUnsignedShort());
        //总电流
        vehicleData.setTotalCurrent(byteBuf.readUnsignedShort());
        //SOC电量
        vehicleData.setSoc(byteBuf.readUnsignedByte());
        //DC-DC状态
        vehicleData.setDcStatus(byteBuf.readUnsignedByte());
        //档位
        vehicleData.setGears(byteBuf.readUnsignedByte());
        //绝缘电阻
        vehicleData.setInsulationResistance(byteBuf.readUnsignedShort());
        //加速行程值
        vehicleData.setAccelerationValue(byteBuf.readUnsignedByte());
        //制动踏板状态
        vehicleData.setBrakePedalCondition(byteBuf.readUnsignedByte());
        return vehicleData;
    }

    @Override
    public ByteBuf encode() throws BusinessException{
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(vehicleStatus);
        buffer.writeByte(chargeStatus);
        buffer.writeByte(operationMode);
        buffer.writeShort(speed);
        buffer.writeInt(mileage.intValue());
        buffer.writeShort(totalVoltage);
        buffer.writeShort(totalCurrent);
        buffer.writeByte(soc);
        buffer.writeByte(dcStatus);
        buffer.writeByte(gears);
        buffer.writeShort(insulationResistance);
        buffer.writeByte(accelerationValue);
        buffer.writeByte(accelerationValue);
        return buffer;
    }
}
