package com.crsardar.java.dao;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private List<Order> orderList = new ArrayList<>();

    private static long ids = 3;

    @PostConstruct
    public void initDB() {
        orderList.add(new Order(1, "Pencil", 100));
        orderList.add(new Order(2, "Pen", 300));
        orderList.add(new Order(3, "Book", 350));
    }

    public Order addOrder(Order order) {

        ids++;
        order.setId(ids);

        orderList.add(order);

        return order;
    }

    public List<Order> getOrders() {

        return orderList;
    }
}
