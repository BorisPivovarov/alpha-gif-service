package ru.boris.alphagifservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AlphaGifServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlphaGifServiceApplication.class, args);
    }
}
