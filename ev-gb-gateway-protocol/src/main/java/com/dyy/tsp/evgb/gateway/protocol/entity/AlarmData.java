package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.common.util.ByteUtil;
import com.dyy.tsp.evgb.gateway.protocol.common.Constants;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 报警数据
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class AlarmData implements IStatus {

    @ApiModelProperty(value = "报警等级", example = "1")
    private Short level;

    @ApiModelProperty(value = "报警通用标志", example = "1")
    private Long alarmInfo;

    @ApiModelProperty(value = "温度差异报警")
    private Boolean temperatureDifferential;

    @ApiModelProperty(value = "电池高温报警")
    private Boolean batteryHighTemperature;

    @ApiModelProperty(value = "车载储能装置类型过压报警")
    private Boolean deviceTypeOverVoltage;

    @ApiModelProperty(value = "车载储能装置类型欠压报警")
    private Boolean deviceTypeUnderVoltage;

    @ApiModelProperty(value = "SOC过低报警")
    private Boolean socLow;

    @ApiModelProperty(value = "单体电池过压报警")
    private Boolean monomerBatteryOverVoltage;

    @ApiModelProperty(value = "单体电池欠压报警")
    private Boolean monomerBatteryUnderVoltage;

    @ApiModelProperty(value = "SOC过高报警")
    private Boolean socHigh;

    @ApiModelProperty(value = "SOC跳变报警")
    private Boolean socJump;

    @ApiModelProperty(value = "车载储能装置类型不匹配报警")
    private Boolean deviceTypeDontMatch;

    @ApiModelProperty(value = "单体电池一致性差报警")
    private Boolean batteryConsistencyPoor;

    @ApiModelProperty(value = "绝缘报警")
    private Boolean insulation;

    @ApiModelProperty(value = "DC温度报警")
    private Boolean dcTemperature;

    @ApiModelProperty(value = "制动系统报警")
    private Boolean brakingSystem;

    @ApiModelProperty(value = "DC状态报警")
    private Boolean dcStatus;

    @ApiModelProperty(value = "驱动电机控制器温度报警")
    private Boolean driveMotorControllerTemperature;

    @ApiModelProperty(value = "高压互锁报警")
    private Boolean highPressureInterlock;

    @ApiModelProperty(value = "驱动电机温度报警")
    private Boolean driveMotorTemperature;

    @ApiModelProperty(value = "车载储能装置过充报警")
    private Boolean deviceTypeOverFilling;

    @ApiModelProperty(value = "可储能装置故障数N1", example = "1")
    private Short deviceFailuresCount;

    @ApiModelProperty(value = "可储能装置故障信息列表")
    private List<Long> deviceFailuresCodes;

    @ApiModelProperty(value = "驱动电机故障数N2", example = "1")
    private Short driveMotorFailuresCount;

    @ApiModelProperty(value = "驱动电机故障信息列表")
    private List<Long> driveMotorFailuresCodes;

    @ApiModelProperty(value = "发动机故障数N3", example = "1")
    private Short engineFailuresCount;

    @ApiModelProperty(value = "发动机故障信息列表")
    private List<Long> engineFailuresCodes;

    @ApiModelProperty(value = "其他故障数N4", example = "1")
    private Short otherFailuresCount;

    @ApiModelProperty(value = "其他故障信息列表")
    private List<Long> otherFailuresCodes;

    @Override
    public AlarmData decode(ByteBuf byteBuf) throws BusinessException {
        AlarmData alarmData = new AlarmData();
        alarmData.setLevel(byteBuf.readUnsignedByte());
        Long l = byteBuf.readUnsignedInt();
        alarmData.setAlarmInfo(l);
        char[] chars = ByteUtil.to32BinaryString(l.intValue()).toCharArray();
        alarmData.setTemperatureDifferential(Constants.CHAR_49 == (chars[chars.length-1]));
        alarmData.setBatteryHighTemperature(Constants.CHAR_49 == (chars[chars.length-2]));
        alarmData.setDeviceTypeOverVoltage(Constants.CHAR_49 == (chars[chars.length-3]));
        alarmData.setDeviceTypeUnderVoltage(Constants.CHAR_49 == (chars[chars.length-4]));
        alarmData.setSocLow(Constants.CHAR_49 == (chars[chars.length-5]));
        alarmData.setMonomerBatteryOverVoltage(Constants.CHAR_49 == (chars[chars.length-6]));
        alarmData.setMonomerBatteryUnderVoltage(Constants.CHAR_49 == (chars[chars.length-7]));
        alarmData.setSocHigh(Constants.CHAR_49 == (chars[chars.length-8]));
        alarmData.setSocJump(Constants.CHAR_49 == (chars[chars.length-9]));
        alarmData.setDeviceTypeDontMatch(Constants.CHAR_49 == (chars[chars.length-10]));
        alarmData.setBatteryConsistencyPoor(Constants.CHAR_49 == (chars[chars.length-11]));
        alarmData.setInsulation(Constants.CHAR_49 == (chars[chars.length-12]));
        alarmData.setDcTemperature(Constants.CHAR_49 == (chars[chars.length-13]));
        alarmData.setBrakingSystem(Constants.CHAR_49 == (chars[chars.length-14]));
        alarmData.setDcStatus(Constants.CHAR_49 == (chars[chars.length-15]));
        alarmData.setDriveMotorControllerTemperature(Constants.CHAR_49 == (chars[chars.length-16]));
        alarmData.setHighPressureInterlock(Constants.CHAR_49 == (chars[chars.length-17]));
        alarmData.setDriveMotorTemperature(Constants.CHAR_49 == (chars[chars.length-18]));
        alarmData.setDeviceTypeOverFilling(Constants.CHAR_49 == (chars[chars.length-19]));
        short deviceFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setDeviceFailuresCount(deviceFailuresTotal);
        List<Long> deviceFailuresList =new ArrayList<Long>(deviceFailuresTotal);
        for (int i = 0; i <deviceFailuresTotal; i++) {
            deviceFailuresList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setDeviceFailuresCodes(deviceFailuresList);
        short driveMotorFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setDriveMotorFailuresCount(driveMotorFailuresTotal);
        List<Long> driveMotorFailuresList =new ArrayList<Long>(driveMotorFailuresTotal);
        for (int i = 0; i <driveMotorFailuresTotal; i++) {
            driveMotorFailuresList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setDriveMotorFailuresCodes(driveMotorFailuresList);
        short engineFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setEngineFailuresCount(engineFailuresTotal);
        List<Long> engineFailuresList =new ArrayList<Long>(engineFailuresTotal);
        for (int i = 0; i <engineFailuresTotal; i++) {
            engineFailuresList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setEngineFailuresCodes(engineFailuresList);
        short otherFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setOtherFailuresCount(otherFailuresTotal);
        List<Long> otherFailuresTotalList =new ArrayList<Long>(otherFailuresTotal);
        for (int i = 0; i <otherFailuresTotal; i++) {
            otherFailuresTotalList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setOtherFailuresCodes(otherFailuresTotalList);
        return alarmData;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(level);
        buffer.writeInt(alarmInfo.intValue());
        buffer.writeByte(deviceFailuresCount);
        if(deviceFailuresCount>0 && deviceFailuresCodes.size()>0){
            for (int i = 0; i < deviceFailuresCount; i++) {
                buffer.writeInt(deviceFailuresCodes.get(i).intValue());
            }
        }
        buffer.writeByte(driveMotorFailuresCount);
        if(driveMotorFailuresCount>0 && driveMotorFailuresCodes.size()>0){
            for (int i = 0; i < driveMotorFailuresCount; i++) {
                buffer.writeInt(driveMotorFailuresCodes.get(i).intValue());
            }
        }
        buffer.writeByte(engineFailuresCount);
        if(engineFailuresCount>0 && engineFailuresCodes.size()>0){
            for (int i = 0; i < engineFailuresCount; i++) {
                buffer.writeInt(engineFailuresCodes.get(i).intValue());
            }
        }
        buffer.writeByte(otherFailuresCount);
        if(otherFailuresCount>0 && otherFailuresCodes.size()>0){
            for (int i = 0; i < otherFailuresCount; i++) {
                buffer.writeInt(otherFailuresCodes.get(i).intValue());
            }
        }
        return buffer;
    }
}
