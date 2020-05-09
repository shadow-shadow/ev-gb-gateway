package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.RealTimeData;
import lombok.Data;

/**
 * 实时数据上报
 */
@Data
public class RealTimeDataVo extends RealTimeData {

    private String vin;

    private String commandType;

}
