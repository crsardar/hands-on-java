package com.crsardar.java.feature.camel.get.controller;

import com.crsardar.java.feature.camel.get.service.DemoGetService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoGetController extends RouteBuilder {

    @Autowired
    private DemoGetService demoGetService;


    @Override
    public void configure() throws Exception {
        rest().tag("Demo API")
                .get("/get").description("Demo GET API.")
                .param().name("param_1").type(RestParamType.query)
                .dataType("String").endParam()
                .param().name("param_2").type(RestParamType.query)
                .dataType("String").endParam()
                .route()
                .process(demoGetService)
                .endRest();
    }
}