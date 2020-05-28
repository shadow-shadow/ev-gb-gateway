package com.dyy.tsp.evgb.gateway.protocol.dto;

import com.dyy.tsp.evgb.gateway.protocol.entity.QueryParamsResponse;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandDownHelperType;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 指令下发请求到Tbox后,Tbox给出的响应结果。网关将响应结果发送给远程控制服务。
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class CommandDownResponse {

    @ApiModelProperty(value = "车架号", example = "LSFGHHH0123456789")
    private String vin;

    @ApiModelProperty(value = "指令类型", example = "QUERY_COMMAND")
    private CommandDownHelperType command;

    @ApiModelProperty(value = "指令请求时间", example = "1590478622000")
    private Long reuqestTime;

    @ApiModelProperty(value = "响应结果",example = "SUCCESS")
    private ResponseType responseType;

    @ApiModelProperty(value = "查询参数响应结果")
    private QueryParamsResponse queryParamsResponse;

    @ApiModelProperty(value = "指令响应时间", example = "1590478625000")
    private Long responseTime;

    public CommandDownResponse() {

    }

    public CommandDownResponse(String vin, CommandDownHelperType command) {
        this.vin = vin;
        this.command = command;
    }
}
