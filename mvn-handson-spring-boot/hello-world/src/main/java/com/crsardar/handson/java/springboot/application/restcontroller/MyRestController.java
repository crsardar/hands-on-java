package com.crsardar.handson.java.springboot.application.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController
{

    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public String helloWorld(){

        System.out.println("Call to helloWorld()");

        return "Hello World, Welcome to the world of SpringBoot!";
    }

    boolean infinite = true;

    @RequestMapping(method = RequestMethod.GET, path = "/infinite")
    public String helloInfinite(){

        while (infinite){

            System.out.println("I am looping....");
        }

        return "Hello World, Welcome to the world of SpringBoot!";
    }
}
