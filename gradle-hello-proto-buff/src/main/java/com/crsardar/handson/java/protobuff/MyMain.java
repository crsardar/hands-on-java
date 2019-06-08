package com.crsardar.handson.java.protobuff;

import java.io.*;
import java.util.UUID;

public class MyMain {

    public static void main(String... agr) {

        PersonContainer.Person.Builder personBuilder = PersonContainer.Person.newBuilder();
        personBuilder.setFirstName("Chittaranjan");
        personBuilder.setLastName("Sardar");
        personBuilder.setUuid(UUID.randomUUID().toString());

        PersonContainer.Person person = personBuilder.build();

        System.out.println("---> Before Writing : " + person.toString());

        File file = new File("proto_out.obj");
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            person.writeTo(fileOutputStream);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Reading from file");

        try {

            FileInputStream fileInputStream = new FileInputStream(file);
            PersonContainer.Person newMe = PersonContainer.Person.parseFrom(fileInputStream);
            System.out.println("===> What is read from file : " + newMe.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
