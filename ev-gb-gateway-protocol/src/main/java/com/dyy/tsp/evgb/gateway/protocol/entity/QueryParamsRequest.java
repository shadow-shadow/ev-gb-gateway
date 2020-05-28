package com.dyy.tsp.evgb.gateway.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dyy.tsp.netty.common.IStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 国标拓展查询参数请求
 * created by dyy
 */
@Data
@SuppressWarnings("all")
public class QueryParamsRequest implements IStatus {

    @ApiModelProperty(value = "查询参数请求时间")
    @JsonIgnore
    private BeanTime beanTime;

    @ApiModelProperty(value = "查询参数总数", example = "16")
    private Short count;

    @ApiModelProperty(value = "查询参数ID集合")
    private List<Short> ids;

    @JSONField(serialize = false)
    @JsonIgnore
    private BeanTime producer = new BeanTime();

    @Override
    public QueryParamsRequest decode(ByteBuf byteBuf) {
        QueryParamsRequest queryParamsRequest = new QueryParamsRequest();
        queryParamsRequest.setBeanTime(producer.decode(byteBuf));
        queryParamsRequest.setCount(byteBuf.readUnsignedByte());
        if(queryParamsRequest.getCount()>0){
            List<Short> ids = new ArrayList<>();
            for (int i = 0; i < queryParamsRequest.getCount(); i++) {
                ids.add(byteBuf.readUnsignedByte());
            }
            queryParamsRequest.setIds(ids);
        }
        return queryParamsRequest;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeByte(count);
        if(count>0 && !CollectionUtils.isEmpty(ids)){
            for (Short id : ids) {
                buffer.writeByte(id);
            }
        }
        return buffer;
    }
}
