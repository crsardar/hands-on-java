package com.crsardar.java.dao;


public class Address {

    private String addLine1;
    private String addLine2;
    private int pin;

    public Address() {
    }

    public Address(String addLine1, String addLine2, int pin) {
        this.addLine1 = addLine1;
        this.addLine2 = addLine2;
        this.pin = pin;
    }


    public String getAddLine1() {
        return addLine1;
    }

    public void setAddLine1(String addLine1) {
        this.addLine1 = addLine1;
    }


    public String getAddLine2() {
        return addLine2;
    }

    public void setAddLine2(String addLine2) {
        this.addLine2 = addLine2;
    }


    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addLine1='" + addLine1 + '\'' +
                ", addLine2='" + addLine2 + '\'' +
                ", pin=" + pin +
                '}';
    }
}
