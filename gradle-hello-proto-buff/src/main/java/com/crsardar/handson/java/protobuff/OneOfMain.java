package com.crsardar.handson.java.protobuff;

import java.util.UUID;

public class OneOfMain {

    public static void main(String... arg){


        OneOfProto.Employee.Builder builder = OneOfProto.Employee.newBuilder();

        OneOfProto.Engineer engineer = OneOfProto.Engineer.newBuilder().setFirstName("Chittaranjan").setLastName("Sardar").setUuid(UUID.randomUUID().toString()).build();
        OneOfProto.Executive executive = OneOfProto.Executive.newBuilder().setFirstName("Chitta").setLastName("Sardar").setUuid(UUID.randomUUID().toString()).build();

        builder.setExecutive(executive);
        builder.setEngineer(engineer);

        OneOfProto.Employee employee = builder.build();

        System.out.println("Engineer = " + employee.hasEngineer());
        System.out.println("Engineer = " + employee.getEngineer());

        System.out.println("Executive = " + employee.hasExecutive());
        System.out.println("Executive = " + employee.getExecutive());
    }
}
