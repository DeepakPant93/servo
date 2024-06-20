package com.stereotype.servo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan(value = "com.stereotype.servo.command")
public class ServoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServoApplication.class, args);
    }

}
