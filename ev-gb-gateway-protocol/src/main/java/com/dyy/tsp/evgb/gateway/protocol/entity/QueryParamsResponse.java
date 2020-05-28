package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.netty.common.IStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 国标拓展查询参数响应
 */
@Data
@SuppressWarnings("all")
public class QueryParamsResponse implements IStatus {

    @ApiModelProperty(value = "查询参数响应时间")
    private BeanTime beanTime;

    @ApiModelProperty(value = "查询参数响应参数个数", example = "16")
    private Short count;

    @ApiModelProperty(value = "查询参数响应信息")
    private Params params;

    @JSONField(serialize = false)
    @JsonIgnore
    private BeanTime beanTimeProducer = new BeanTime();

    @JSONField(serialize = false)
    @JsonIgnore
    private Params paramsProducer = new Params();

    @Override
    public QueryParamsResponse decode(ByteBuf byteBuf) {
        QueryParamsResponse response = new QueryParamsResponse();
        response.setBeanTime(beanTimeProducer.decode(byteBuf));
        response.setCount(byteBuf.readUnsignedByte());
        if(response.getCount()>0){
            response.setParams(paramsProducer.decode(byteBuf));
        }
        return response;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeByte(count);
        if(count!=null && count>0){
            buffer.writeBytes(params.encode());
        }
        return buffer;
    }

}
