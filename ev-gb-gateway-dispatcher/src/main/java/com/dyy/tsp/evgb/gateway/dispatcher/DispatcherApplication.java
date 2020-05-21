package com.dyy.tsp.evgb.gateway.dispatcher;

import com.dyy.tsp.evgb.gateway.dispatcher.config.DispatcherProperties;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableConfigurationProperties(value = {DispatcherProperties.class})
@SpringBootApplication
@EnableDiscoveryClient
public class DispatcherApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DispatcherApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
