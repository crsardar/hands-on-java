package com.crsardar.handson.java.general.reflexion;

public class MyImpl {

    public void action(final SubClassOne subClassOne){

        subClassOne.print();
    }

    public void action(final SubClassTwo subClassTwo){

        subClassTwo.print();
    }

    public void action(final SuperClass superClass){

        superClass.print();
    }
}
