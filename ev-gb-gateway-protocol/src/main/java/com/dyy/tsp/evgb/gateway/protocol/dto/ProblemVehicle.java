package com.dyy.tsp.evgb.gateway.protocol.dto;

import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问题车辆实体
 * created by dyy
 */
@Data
public class ProblemVehicle {

    @ApiModelProperty(value = "车架号", example = "LSFGHHH0123456789")
    private String vin;

    @ApiModelProperty(value = "采集时间", example = "1590478622000")
    private Long collectTime;

    @ApiModelProperty(value = "指令类型", example = "VEHICLE_LOGIN")
    private CommandType commandType;

    public ProblemVehicle() {
    }

    public ProblemVehicle(String vin, Long collectTime, CommandType commandType) {
        this.vin = vin;
        this.collectTime = collectTime;
        this.commandType = commandType;
    }
}
