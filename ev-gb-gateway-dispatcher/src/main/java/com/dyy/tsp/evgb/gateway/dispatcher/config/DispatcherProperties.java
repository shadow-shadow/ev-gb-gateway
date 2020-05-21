package com.dyy.tsp.evgb.gateway.dispatcher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("all")
@Data
@ConfigurationProperties(prefix = DispatcherProperties.PREFIX)
public class DispatcherProperties {

    public static final String PREFIX = "dispatcher";

    //历史,最新车辆业务数据
    private String businessTopic = "vehicle_business_data";

    //驾驶记录/充电记录数据
    private String drivingBehaviorTopic = "vehicle_driving_behavior_data";

    //历史,最新位置数据
    private String locationTopic = "vehicle_location_data";

    //历史原始报文数据
    private String originalMessageTopic = "vehicle_original_message_data";

    //是否开始消息投递回调
    private Boolean callBackFlag = Boolean.FALSE;

}
