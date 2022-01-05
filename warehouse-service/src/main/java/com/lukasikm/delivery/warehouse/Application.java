package com.lukasikm.delivery.warehouse;

import com.lukasikm.delivery.orderserviceclient.OrderClientExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackages = "com.lukasikm.delivery.orderserviceclient")
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    OrderClientExceptionHandler orderClientExceptionHandler() {
        return new OrderClientExceptionHandler();
    }
}
