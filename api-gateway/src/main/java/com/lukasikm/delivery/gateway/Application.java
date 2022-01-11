package com.lukasikm.delivery.gateway;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.*;

@EnableFeignClients(basePackages = "com.lukasikm.delivery.authserviceclient")
@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    DiscoveryClient discoveryClient;

    @Bean
    public List<GroupedOpenApi> apis() {
        var groups = new ArrayList<GroupedOpenApi>();
        List<String> definitions = discoveryClient.getServices();

        definitions.stream().filter(service -> service.matches(".*-service")).forEach(service -> {
            var name = service.replaceAll("-service", "");

            groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
        });
        return groups;
    }
}
