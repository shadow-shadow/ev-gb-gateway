package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.common.exception.BusinessException;
import com.dyy.tsp.evgb.gateway.protocol.common.Constants;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.nio.ByteOrder;

@Data
@SuppressWarnings("all")
public class Params implements IStatus {

    @ApiModelProperty(value = "车载终端本地存储时间周期，有效值范围：0～60000（表示 0ms～ 60000ms），最小计量单元：1ms，“ 0xFF,0xFE”表示异常，“0xFF,0xFF” 表示无效。 ", example = "1")
    protected Integer localStorageTimeCycleOfVehicleTerminal;

    @ApiModelProperty(value = "正常时，信息上报时间周期，有效值范围：1～600（表示 1s～600s）， 最小计量单元：1s，“ 0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。", example = "1")
    protected Integer informationReportingCycle;

    @ApiModelProperty(value = "出现报警时，信息上报时间周期，有效值范围：0～60000（表示 0ms～ 60000ms），最小计量单元：1ms，“ 0xFF,0xFE”表示异常，“0xFF,0xFF” 表示无效。 0x04 ", example = "1")
    protected Integer alarmInformationReportingCycle;

    @ApiModelProperty(value = "远程服务不管理平台域名长度", example = "13")
    protected Short remoteServiceManagementPlatformHostLength;

    @ApiModelProperty(value = "远程服务不管理平台域名", example = "www.baidu.com")
    protected String remoteServiceManagementPlatformHost;

    @ApiModelProperty(value = "远程服务不管理平台端口，有效值范围：0～65531，“ 0xFF,0xFE”表示 异常，“0xFF,0xFF”表示无效。 ", example = "80")
    protected Integer remoteServiceManagementPlatformPort;

    @ApiModelProperty(value = "硬件版本", example = "12345")
    protected String hardwareVersion;

    @ApiModelProperty(value = "固件版本", example = "12345")
    protected String softwareVersion;

    @ApiModelProperty(value = "车载终端心跳収送周期，有效值范围：1～240（表示 1s～240s），最小 计量单元：1s，“ 0xFE”表示异常，“0xFF”表示无效。", example = "1")
    protected Short heartbeatSendingCycle;

    @ApiModelProperty(value = "终端应答超时时间，有效值范围：1～600（表示 1s～600s），最小计量 单元：1s，“ 0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。 ", example = "5")
    protected Integer tboxResponseTimeOut;

    @ApiModelProperty(value = "平台应答超时时间，有效值范围：1～600（表示 1s～600s），最小计量 单元：1s，“ 0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效", example = "6")
    protected Integer tspResponseTimeOut;

    @ApiModelProperty(value = "连续三次登入失败后，到下一次登入的间隔时间。有效值范围：1～240 （表示 1min～240min），最小计量单元： 1min，“ 0xFE”表示异常，“0xFF” 表示无效。 ", example = "3")
    protected Short loginTimeInterval;

    @ApiModelProperty(value = "公共平台域名长度 N。 ", example = "13")
    protected Short commonPlatformHostLength;

    @ApiModelProperty(value = "公共平台域名", example = "www.baidu.com")
    protected String commonPlatformHost;

    @ApiModelProperty(value = "公共平台端口", example = "80")
    protected Integer commonPlatformPort;

    @ApiModelProperty(value = "是否处于抽样监测中“0x01”表示是，“0x02”表示否，“0xFE”表示异 常，“0xFF”表示无效。 ", example = "2")
    protected Short underSamplingMonitoring;

    @Override
    public Params decode(ByteBuf byteBuf) {
        Params params = new Params();
        while (byteBuf.isReadable()) {
            short id = byteBuf.readUnsignedByte();
            switch (id){
                case 1:{
                    params.setLocalStorageTimeCycleOfVehicleTerminal(byteBuf.readUnsignedShort());
                    break;
                }
                case 2:{
                    params.setInformationReportingCycle(byteBuf.readUnsignedShort());
                    break;
                }
                case 3:{
                    params.setAlarmInformationReportingCycle(byteBuf.readUnsignedShort());
                    break;
                }
                case 4:{
                    params.setRemoteServiceManagementPlatformHostLength(byteBuf.readUnsignedByte());
                    break;
                }
                case 5:{
                    if(params.getRemoteServiceManagementPlatformHostLength()>0){
                        params.setRemoteServiceManagementPlatformHost(byteBuf.readSlice(params.getRemoteServiceManagementPlatformHostLength()).toString(Constants.UTF_8));
                    }
                    break;
                }
                case 6:{
                    params.setRemoteServiceManagementPlatformPort(byteBuf.readUnsignedShort());
                    break;
                }
                case 7:{
                    params.setHardwareVersion(byteBuf.readSlice(5).toString(Constants.UTF_8));
                    break;
                }
                case 8:{
                    params.setSoftwareVersion(byteBuf.readSlice(5).toString(Constants.UTF_8));
                    break;
                }
                case 9:{
                    params.setHeartbeatSendingCycle(byteBuf.readUnsignedByte());
                    break;
                }
                case 10:{
                    params.setTboxResponseTimeOut(byteBuf.readUnsignedShort());
                    break;
                }
                case 11:{
                    params.setTspResponseTimeOut(byteBuf.readUnsignedShort());
                    break;
                }
                case 12:{
                    params.setLoginTimeInterval(byteBuf.readUnsignedByte());
                    break;
                }
                case 13:{
                    params.setCommonPlatformHostLength(byteBuf.readUnsignedByte());
                    break;
                }
                case 14:{
                    if(params.getCommonPlatformHostLength()>0){
                        params.setCommonPlatformHost(byteBuf.readSlice(params.getCommonPlatformHostLength()).toString(Constants.UTF_8));
                    }
                    break;
                }
                case 15:{
                    params.setCommonPlatformPort(byteBuf.readUnsignedShort());
                    break;
                }
                case 16:{
                    params.setUnderSamplingMonitoring(byteBuf.readUnsignedByte());
                    break;
                }
                default:
                    throw new BusinessException("Unknown Params id : " + id);
            }
        }
        return params;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        if(localStorageTimeCycleOfVehicleTerminal!=null){
            buffer.writeByte(1);
            buffer.writeShort(localStorageTimeCycleOfVehicleTerminal);
        }
        if(informationReportingCycle!=null){
            buffer.writeByte(2);
            buffer.writeShort(informationReportingCycle);
        }
        if(alarmInformationReportingCycle!=null){
            buffer.writeByte(3);
            buffer.writeShort(alarmInformationReportingCycle);
        }
        if(remoteServiceManagementPlatformHostLength!=null && remoteServiceManagementPlatformHostLength>0){
            buffer.writeByte(4);
            buffer.writeByte(remoteServiceManagementPlatformHostLength);
            buffer.writeByte(5);
            buffer.writeBytes(remoteServiceManagementPlatformHost.getBytes());
            buffer.writeByte(6);
            buffer.writeShort(remoteServiceManagementPlatformPort);
        }
        if(StringUtils.isNotBlank(hardwareVersion)){
            buffer.writeByte(7);
            buffer.writeBytes(hardwareVersion.getBytes());
        }
        if(StringUtils.isNotBlank(softwareVersion)){
            buffer.writeByte(8);
            buffer.writeBytes(softwareVersion.getBytes());
        }
        if(heartbeatSendingCycle!=null){
            buffer.writeByte(9);
            buffer.writeByte(heartbeatSendingCycle);
        }
        if(tboxResponseTimeOut!=null){
            buffer.writeByte(10);
            buffer.writeShort(tboxResponseTimeOut);
        }
        if(tspResponseTimeOut!=null){
            buffer.writeByte(11);
            buffer.writeShort(tspResponseTimeOut);
        }
        if(loginTimeInterval!=null){
            buffer.writeByte(12);
            buffer.writeByte(loginTimeInterval);
        }
        if(commonPlatformHostLength!=null && commonPlatformHostLength>0){
            buffer.writeByte(13);
            buffer.writeByte(commonPlatformHostLength);
            buffer.writeByte(14);
            buffer.writeBytes(commonPlatformHost.getBytes());
            buffer.writeByte(15);
            buffer.writeShort(commonPlatformPort);
        }
        if(underSamplingMonitoring!=null){
            buffer.writeByte(16);
            buffer.writeByte(underSamplingMonitoring);
        }
        return buffer;
    }
}
