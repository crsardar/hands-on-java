package com.crsardar.handson.java.general.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Producer(List) extends.
 * Consumer(List) super.
 */

public class WildCard {

    public void read(List<? extends Number> producerList){

        // List<? extends Number> producerList == Read values must be Number or its Subclass.
        // Can not write anything.

        Integer intVal = 9;
        // producerList.add(intVal); - Can NOT add anything, Only Reading
        Number number = producerList.get(0);
    }

    public void write(List<? super Number> consumerList){

        // List<? super Number> consumerList == Written values must be Number or its Subclass.
        // Read values are unpredictable.

        consumerList.add(new Integer(10));
        consumerList.add(new Double(20));
        consumerList.add(new Float(10));
    }

    public void test(){

        WildCard wildCard = new WildCard();

        List<Number> values = new ArrayList<>();
        wildCard.write(values);
        wildCard.read(values);
    }
}
