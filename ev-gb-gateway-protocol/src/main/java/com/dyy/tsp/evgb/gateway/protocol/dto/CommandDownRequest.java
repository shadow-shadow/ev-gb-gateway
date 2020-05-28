package com.dyy.tsp.evgb.gateway.protocol.dto;

import com.dyy.tsp.evgb.gateway.protocol.entity.QueryParamsRequest;
import com.dyy.tsp.evgb.gateway.protocol.entity.SetParamsRequest;
import com.dyy.tsp.evgb.gateway.protocol.entity.TerminalControlType;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandDownHelperType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 远程控制服务与网关指令下发交互对象
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class CommandDownRequest {

    @ApiModelProperty(value = "车架号", example = "LSFGHHH0123456789")
    private String vin;

    @ApiModelProperty(value = "指令类型", example = "QUERY_COMMAND")
    private CommandDownHelperType command;

    @ApiModelProperty(value = "指令请求时间", example = "1590478622000")
    private Long time;

    @ApiModelProperty(value = "当为车载终端控制时请求参数")
    private TerminalControlType terminalControlType;

    @ApiModelProperty(value = "当为查询参数指令时请求参数")
    private QueryParamsRequest queryParamsRequest;

    @ApiModelProperty(value = "当为设置参数指令时请求参数")
    private SetParamsRequest setParamsRequest;

    public CommandDownRequest() {

    }

    public CommandDownRequest(String vin, CommandDownHelperType command) {
        this.vin = vin;
        this.command = command;
    }
}
