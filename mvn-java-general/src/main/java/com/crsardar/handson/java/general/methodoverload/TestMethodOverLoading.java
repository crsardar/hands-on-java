package com.crsardar.handson.java.general.methodoverload;

import java.lang.reflect.Method;

public class TestMethodOverLoading {


    public void method(final SuperClass superClass,  SubClassOne subClassOne){

        System.out.println("in > public void method(final SubClassOne subClassOne) ");
    }

    public void method(final SubClassTwo subClassTwo){

        System.out.println("in > public void method(final SubClassTwo subClassTwo)");
    }

    public void method(final SuperClass superClass){

        System.out.println("in > public void method(final SuperClass superClass)");

        System.out.println(superClass + " = " + superClass.getClass().getSimpleName());

    }

    public static void main(String... arg){



        SuperClass superClass = new SubClassTwo();
        try {

            Method method = TestMethodOverLoading.class.getMethod("method", SuperClass.class, SubClassOne.class);

            method.invoke(null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        TestMethodOverLoading testMethodOverLoading = new TestMethodOverLoading();

        testMethodOverLoading.method(superClass); // TODO - See carefully it is a Compilation Error

    }
}
