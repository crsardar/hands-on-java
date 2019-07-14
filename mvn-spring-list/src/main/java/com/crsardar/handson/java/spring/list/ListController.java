package com.crsardar.handson.java.spring.list;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ListController {


    @GetMapping(path = "api-check")
    public String apiCheck(){

        return "Hi, 'mvn-spring-list' is up!";
    }

    @GetMapping(path = "list")
    public List<ListItem> getList(){

        List<ListItem> listItems = new ArrayList<>();
        for(int i = 0; i < 100; i++){

            ListItem listItem = new ListItem(i, "This is list item - " + i);
            listItems.add(listItem);
        }

        return listItems;
    }
}
