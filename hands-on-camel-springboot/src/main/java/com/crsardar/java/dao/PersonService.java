package com.crsardar.java.dao;

import java.util.ArrayList;
import java.util.List;


public class PersonService {

    private List<Person> personList = new ArrayList<>();

    public Person getPerson(int id) {

        Address address = new Address( "Add line 1 " + id, "Add line 2 " + id, id);
        Company company = new Company("Company " + id, address);
        Person person = new Person("Person " + id, company);

        personList.add(person);

        return person;
    }

    public void addPerson(Person person){

        personList.add(person);
    }

    public List<Person> getPersons(){

        return personList;
    }
}
