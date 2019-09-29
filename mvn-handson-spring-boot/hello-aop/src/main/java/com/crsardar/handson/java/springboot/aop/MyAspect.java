package com.crsardar.handson.java.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

/**
 * @author Chittaranjan Sardar
 */

@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.crsardar.handson.java.springboot.controller.IRestController.*(..))")
    public void executionPointcut(){

    }


    @Before("executionPointcut()")
    public void beforeAdvice(){

        System.out.println("\n\n\tMyAspect : beforeAdvice");
    }

    @AfterThrowing(pointcut="executionPointcut()",
            throwing="th")
    public void afterThrowing(JoinPoint joinPoint, Throwable th){

        System.out.println("\n\n\tMyAspect : afterThrowing \n\n");

    }

    /* Working

    @Around("executionPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

        System.out.println("\n\n\tMyAspect : aroundAdvice");

        String value;

        try {

            value = (String) proceedingJoinPoint.proceed();

        }catch (Throwable th){

            System.out.println("\t" + th);

            value = th.getMessage();

        }

        return value;
    }
    */
}
