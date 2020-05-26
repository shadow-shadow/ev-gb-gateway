package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.nio.ByteOrder;

/**
 * 设置参数指令下发
 */
@Data
@SuppressWarnings("all")
public class SetParamsRequest implements IStatus {

    @ApiModelProperty(value = "设置参数请求时间")
    private BeanTime beanTime;

    @ApiModelProperty(value = "设置参数个数", example = "16")
    private Short count;

    @ApiModelProperty(value = "设置参数列表")
    private Params params;

    @JSONField(serialize = false)
    private BeanTime beanTimeProducer = new BeanTime();

    @JSONField(serialize = false)
    private Params paramsProducer = new Params();

    @Override
    public SetParamsRequest decode(ByteBuf byteBuf) {
        SetParamsRequest request = new SetParamsRequest();
        request.setBeanTime(beanTimeProducer.decode(byteBuf));
        request.setCount(byteBuf.readUnsignedByte());
        if(request.getCount()>0){
            request.setParams(paramsProducer.decode(byteBuf));
        }
        return request;
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
