package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.RealTimeData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实时数据上报
 */
@Data
public class RealTimeDataVo extends RealTimeData {

    @ApiModelProperty(value = "车架号",example = "LSFGHHH0123456789")
    private String vin;

    @ApiModelProperty(value = "指令类型",example = "REALTIME_DATA_REPORTING")
    private String commandType;

}
