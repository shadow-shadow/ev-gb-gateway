package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.VehicleLogin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VehicleLoginVo extends VehicleLogin {

    @ApiModelProperty(value = "车架号",example = "LSFGHHH0123456789")
    private String vin;

}
