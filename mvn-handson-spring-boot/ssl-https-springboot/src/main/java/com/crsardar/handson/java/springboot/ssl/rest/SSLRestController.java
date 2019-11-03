package com.crsardar.handson.java.springboot.ssl.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chittaranjan Sardar
 */
@RestController
public class SSLRestController {

    @GetMapping(path = "hello-ssl")
    public String hiSSL() {

        System.out.println("---> Hi SSL");


        return "Hello There! Welcome to Spring Boot SSL!";
    }
}
