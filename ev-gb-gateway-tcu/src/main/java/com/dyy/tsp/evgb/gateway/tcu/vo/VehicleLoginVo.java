package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.VehicleLogin;
import lombok.Data;

@Data
public class VehicleLoginVo extends VehicleLogin {

    private String vin;

}
