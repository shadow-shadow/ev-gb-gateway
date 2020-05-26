package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.netty.buffer.ByteBuf;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据单元
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class DataBody {

    @ApiModelProperty(value = "数据单元JSON")
    private JSONObject json;

    @ApiModelProperty(value = "数据单元HEX")
    @JSONField(serialize = false)
    private ByteBuf byteBuf;

    public DataBody(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public DataBody(JSONObject json) {
        this.json = json;
    }

    public DataBody(JSONObject json, ByteBuf byteBuf) {
        this.json = json;
        this.byteBuf = byteBuf;
    }
}
