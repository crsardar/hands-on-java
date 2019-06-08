package com.crsardar.handson.java.general.reflexion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflexion {

    public static void main(String... arg){

        try {

            MyImpl myImpl = new MyImpl();

            Method method1 = myImpl.getClass().getMethod("action", SubClassOne.class);
            method1.invoke(myImpl, new SubClassOne());

            Method method2 = myImpl.getClass().getMethod("action", SubClassTwo.class);
            method2.invoke(myImpl, new SubClassTwo());

            Method method3 = myImpl.getClass().getMethod("action", SuperClass.class);
            method3.invoke(myImpl, new SubClassThree()); // TODO - Special care, we do not have any 'action' method for 'SubClassThree'

        } catch (NoSuchMethodException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
