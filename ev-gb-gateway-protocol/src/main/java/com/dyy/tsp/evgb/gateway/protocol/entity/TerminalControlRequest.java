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
 * 车载终端控制请求
 * created by dyy
 */
@SuppressWarnings("all")
@Data
public class TerminalControlRequest implements IStatus {

    @ApiModelProperty(value = "车载终端控制请求时间")
    private BeanTime beanTime;

    @ApiModelProperty(value = "车载终端控制类型")
    private TerminalControlType terminalControlType;

    @JSONField(serialize = false)
    @JsonIgnore
    private TerminalControlType terminalControlTypeProducer = new TerminalControlType();

    @JSONField(serialize = false)
    @JsonIgnore
    private BeanTime timeProducer = new BeanTime();

    @Override
    public TerminalControlRequest decode(ByteBuf byteBuf) {
        TerminalControlRequest terminalControlRequest = new TerminalControlRequest();
        terminalControlRequest.setBeanTime(timeProducer.decode(byteBuf));
        terminalControlRequest.setTerminalControlType(terminalControlTypeProducer.decode(byteBuf));
        return terminalControlRequest;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeBytes(terminalControlType.encode());
        return buffer;
    }
}
