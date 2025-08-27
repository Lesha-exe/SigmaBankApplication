package ru.korona.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SigmaBankApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SigmaBankApp.class, args);
    }
}
