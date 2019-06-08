package com.crsardar.handson.java.general.reflexion;

public class SuperClass {

    public void print(){

        System.out.println(this.getClass().getSimpleName() + " : Hi there!");
    }

}
