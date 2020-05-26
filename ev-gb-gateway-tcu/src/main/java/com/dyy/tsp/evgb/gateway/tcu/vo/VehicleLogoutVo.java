package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.VehicleLogout;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleLogoutVo extends VehicleLogout {

    @ApiModelProperty(value = "车架号",example = "LSFGHHH0123456789")
    private String vin;

}
