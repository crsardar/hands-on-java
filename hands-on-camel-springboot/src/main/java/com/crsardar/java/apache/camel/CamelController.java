package com.crsardar.java.apache.camel;

import com.crsardar.java.dao.Order;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CamelController extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest().post("/order")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .type(Order.class)
                .outType(Order.class)
                .to("bean:orderService?method=addOrder(${body})");

        rest().get("/order")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("bean:orderService?method=getOrders()");

    }
}
