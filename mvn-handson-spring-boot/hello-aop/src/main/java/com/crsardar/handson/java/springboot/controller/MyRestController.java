package com.crsardar.handson.java.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chittaranjan Sardar
 */

@RestController
public class MyRestController implements IRestController{

    @Override
    @GetMapping(path = "hello-aop")
    public String hiAOP() {

        System.out.println("---> hiAOP");


        return "Hello There! Welcome to Spring AOP!";
    }

    @Override
    @GetMapping("hello-throw")
    public String mustThrowException(@RequestParam(value = "name")final String name) throws RuntimeException {

        System.out.println("---> mustThrowException");
        if("Bakasur".equals(name)) {
            throw new RuntimeException("You are not welcome here!");
        }


        return name + " : Welcome to the club!!!";
    }
}
