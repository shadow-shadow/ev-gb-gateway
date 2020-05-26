package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandDownHelperType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 指令下发请求
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

    @ApiModelProperty(value = "指令请求流水号", example = "1")
    private Integer serialNum;

    @ApiModelProperty(value = "查询/设置参数总数", example = "16")
    private Short count;

    @ApiModelProperty(value = "查询参数ID集合")
    private List<Short> ids;

    @ApiModelProperty(value = "设置参数信息")
    private Params params;

    public CommandDownRequest() {
    }

    public CommandDownRequest(String vin, CommandDownHelperType command) {
        this.vin = vin;
        this.command = command;
    }
}
