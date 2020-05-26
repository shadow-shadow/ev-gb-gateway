package com.dyy.tsp.evgb.gateway.tcu.vo;

import com.dyy.tsp.evgb.gateway.protocol.entity.PlatformLogin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台登入
 */
@Data
public class PlatformLoginVo extends PlatformLogin {

    @ApiModelProperty(value = "车架号",example = "LSFGHHH0123456789")
    private String vin;

}
