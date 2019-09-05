/**
 * @author Chittaranjan Sardar
 */

package com.crsardar.spring.all.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ApiController {

    @GetMapping("/")
    public String hello(){

        return "'mvn-spring-all' is working!";
    }

    @GetMapping("/data")
    public static String getData(@RequestParam String name){

        System.out.println("Passed nameQuery = " + name);


        long timeToWait = System.currentTimeMillis() + 100000;

        while (System.currentTimeMillis() < timeToWait){

            System.out.println("Processing for " + name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Done for " + name;
    }
}
