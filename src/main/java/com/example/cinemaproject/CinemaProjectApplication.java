package com.example.cinemaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaProjectApplication.class, args);
    }

}
