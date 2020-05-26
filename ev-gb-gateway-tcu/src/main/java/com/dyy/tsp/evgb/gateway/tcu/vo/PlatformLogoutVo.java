package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.PlatformLogout;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台登出
 */
@Data
public class PlatformLogoutVo  extends PlatformLogout {

    @ApiModelProperty(value = "车架号",example = "LSFGHHH0123456789")
    private String vin;
}
