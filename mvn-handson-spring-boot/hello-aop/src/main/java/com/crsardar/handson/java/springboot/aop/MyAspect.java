package com.crsardar.handson.java.springboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;

import org.springframework.stereotype.Component;

/**
 * @author Chittaranjan Sardar
 */

@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(*  com.crsardar.handson.java.springboot.controller.IRestController.*(..))")
    public void executionPointcut(){

    }

    /* Working Example
    @Before("executionPointcut()")
    public void beforeAdvice(){

        System.out.println("\n\n\t" + getClass().getSimpleName() + " : beforeAdvice");
    }
    */


    @Around("executionPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

        System.out.println("\n\n\t" + getClass().getSimpleName() + " : aroundAdvice");

        String value;

        try {

            value = (String) proceedingJoinPoint.proceed();

        }catch (Throwable th){

            System.out.println("\t" + th);

            value = th.getMessage();

        }

        return value;
    }

}
