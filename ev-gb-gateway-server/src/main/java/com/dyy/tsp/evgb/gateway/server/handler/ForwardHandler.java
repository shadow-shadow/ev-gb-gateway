package com.dyy.tsp.evgb.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.evgb.gateway.protocol.dto.CommandDownResponse;
import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
import com.dyy.tsp.evgb.gateway.server.config.EvGBProperties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;

/**
 * 数据转发处理器
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class ForwardHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForwardHandler.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private EvGBProperties evGBProperties;

    /**
     * 转发数据到远控服务
     * @param commandDownResponse
     */
    public void sendToRemoteResponse(CommandDownResponse commandDownResponse){
        this.send(evGBProperties.getCommandResponseTopic(),JSONObject.toJSONString(commandDownResponse));
    }

    /**
     * 转发数据到Debug
     * @param protocol
     */
    public void sendToDebug(EvGBProtocol protocol){
        protocol.setGatewayForwardTime(Instant.now().toEpochMilli());
        this.send(evGBProperties.getDebugTopic(),JSONObject.toJSONString(protocol));
    }

    /**
     * 转发数据到Dispatcher
     * @param protocol
     */
    public void sendToDispatcher(EvGBProtocol protocol){
        protocol.setGatewayForwardTime(Instant.now().toEpochMilli());
        this.send(evGBProperties.getDispatcherTopic(),JSONObject.toJSONString(protocol));
    }

    private void send(String topic,String message){
        LOGGER.debug("send {} {}",topic,message);
        if(!evGBProperties.getCallBackFlag()){
            kafkaTemplate.send(topic,message);
        }else{
            kafkaTemplate.send(new ProducerRecord(topic, message, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e != null){
                        LOGGER.error("send {} error : {}", ExceptionUtils.getMessage(e));
                    }
                }
            }));
        }
    }
}
