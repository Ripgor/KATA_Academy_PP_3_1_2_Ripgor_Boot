package com.ripgor.ripgor_boot;

import com.ripgor.ripgor_boot.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RipgorBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[] {RipgorBootApplication.class, JpaConfig.class}, args);
    }

}
