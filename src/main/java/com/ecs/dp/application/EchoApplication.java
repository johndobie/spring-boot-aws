package com.ecs.dp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Configuration
@EnableConfigurationProperties
public class EchoApplication {

    public static void main(String[] args) {

        SpringApplication.run(EchoApplication.class, args);
    }

    @GetMapping("/echo")
    public String echo(@RequestParam String message) {

        return message;
    }

}
