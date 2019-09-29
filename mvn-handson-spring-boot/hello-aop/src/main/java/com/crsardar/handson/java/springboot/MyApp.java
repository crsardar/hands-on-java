package com.crsardar.handson.java.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Chittaranjan Sardar
 */

@EnableAspectJAutoProxy
@SpringBootApplication
public class MyApp {

    public static void main(String[] args) {

        SpringApplication.run(MyApp.class, args);
    }

}



