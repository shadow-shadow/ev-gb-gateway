package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.PlatformLogin;
import lombok.Data;

/**
 * 平台登入
 */
@Data
public class PlatformLoginVo extends PlatformLogin {

    private String vin;

}
