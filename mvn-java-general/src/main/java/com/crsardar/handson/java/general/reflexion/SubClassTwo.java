package com.crsardar.handson.java.general.reflexion;

public class SubClassTwo extends SuperClass {

    @Override
    public void print(){

        System.out.println(this.getClass().getSimpleName() + " : Hi there!");
    }

}
