package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.PlatformLogout;
import lombok.Data;

/**
 * 平台登出
 */
@Data
public class PlatformLogoutVo  extends PlatformLogout {

    private String vin;
}
