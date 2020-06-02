package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 燃料电池数据
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class FuelCellData implements IStatus {

    @ApiModelProperty(value = "燃料电池电压",example = "10000")
    private Integer voltage;

    @ApiModelProperty(value = "燃料电池电流",example = "20000")
    private Integer current;

    @ApiModelProperty(value = "燃料消耗率",example = "20000")
    private Integer fuelConsumption;

    @ApiModelProperty(value = "燃料电池温度探针总数",example = "1")
    private Integer temperatureProbeCount;

    @ApiModelProperty(value = "探针温度值")
    private List<Short> probeTemperatures;

    @ApiModelProperty(value = "氢系统中最高温度",example = "140")
    private Integer hydrogenSystemMaxTemperature;

    @ApiModelProperty(value = "氢系统中最高温度探针代号",example = "1")
    private Short hydrogenSystemTemperatureProbeNum;

    @ApiModelProperty(value = "氢气最高浓度",example = "10000")
    private Integer hydrogenSystemMaxConcentration;

    @ApiModelProperty(value = "氢气最高浓度探针代号",example = "1")
    private Short hydrogenSystemConcentrationProbeNum;

    @ApiModelProperty(value = "氢气最高压力",example = "10000")
    private Integer hydrogenSystemMaxPressure;

    @ApiModelProperty(value = "氢气最高压力探针代号",example = "1")
    private Short hydrogenSystemPressureProbeNum;

    @ApiModelProperty(value = "高压DC-DC状态",example = "2")
    private Short dcStatus;

    @Override
    public FuelCellData decode(ByteBuf byteBuf) throws BusinessException {
        FuelCellData fuelCellData = new FuelCellData();
        fuelCellData.setVoltage(byteBuf.readUnsignedShort());
        fuelCellData.setCurrent(byteBuf.readUnsignedShort());
        fuelCellData.setFuelConsumption(byteBuf.readUnsignedShort());
        fuelCellData.setTemperatureProbeCount(byteBuf.readUnsignedShort());
        List<Short> temperatureList = new ArrayList<Short>(fuelCellData.getTemperatureProbeCount());
        if(fuelCellData.getTemperatureProbeCount()>0){
            for (int i = 0; i < fuelCellData.getTemperatureProbeCount(); i++) {
                temperatureList.add(byteBuf.readUnsignedByte());
            }
        }
        fuelCellData.setProbeTemperatures(temperatureList);
        fuelCellData.setHydrogenSystemMaxTemperature(byteBuf.readUnsignedShort());
        fuelCellData.setHydrogenSystemTemperatureProbeNum(byteBuf.readUnsignedByte());
        fuelCellData.setHydrogenSystemMaxConcentration(byteBuf.readUnsignedShort());
        fuelCellData.setHydrogenSystemConcentrationProbeNum(byteBuf.readUnsignedByte());
        fuelCellData.setHydrogenSystemMaxPressure(byteBuf.readUnsignedShort());
        fuelCellData.setHydrogenSystemPressureProbeNum(byteBuf.readUnsignedByte());
        fuelCellData.setDcStatus(byteBuf.readUnsignedByte());
        return fuelCellData;
    }

    @Override
    public ByteBuf encode() throws BusinessException {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeShort(voltage);
        buffer.writeShort(current);
        buffer.writeShort(fuelConsumption);
        buffer.writeShort(temperatureProbeCount);
        if(temperatureProbeCount >0 && probeTemperatures.size()>0){
            for (int i = 0; i <probeTemperatures.size(); i++) {
                buffer.writeByte(probeTemperatures.get(i));
            }
        }
        buffer.writeShort(hydrogenSystemMaxTemperature);
        buffer.writeByte(hydrogenSystemTemperatureProbeNum);
        buffer.writeShort(hydrogenSystemMaxConcentration);
        buffer.writeByte(hydrogenSystemConcentrationProbeNum);
        buffer.writeShort(hydrogenSystemMaxPressure);
        buffer.writeByte(hydrogenSystemPressureProbeNum);
        buffer.writeByte(dcStatus);
        return buffer;
    }
}
