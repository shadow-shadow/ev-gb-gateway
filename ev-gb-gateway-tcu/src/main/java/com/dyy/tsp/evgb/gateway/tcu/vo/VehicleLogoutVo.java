package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.VehicleLogout;
import lombok.Data;

@Data
public class VehicleLogoutVo extends VehicleLogout {

    private String vin;

}
