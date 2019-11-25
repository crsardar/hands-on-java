/**
 * @author Chittaranjan Sardar
 */
package com.crsardar.handson.java.springboot.data;

import com.crsardar.handson.java.springboot.data.dao.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class DataApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(DataApp.class, args);

        org.springframework.data.domain.Page<Person> page = null;
        //page.getContent();
    }
}
