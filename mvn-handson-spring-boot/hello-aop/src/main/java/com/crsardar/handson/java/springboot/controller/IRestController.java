package com.crsardar.handson.java.springboot.controller;

/**
 * @author Chittaranjan Sardar
 */
public interface IRestController {

    public String hiAOP();

    public String mustThrowException(final String name) throws RuntimeException;
}
