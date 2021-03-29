package com.crsardar.java.apache.camel;

import com.crsardar.java.dao.Person;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CamelController extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                // setup context path and port number that netty will use
                .contextPath("/").port(8080)
                // add OpenApi api-doc out of the box
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
                // and enable CORS
                .apiProperty("cors", "true");


        rest("/person")
                .description("Per rest service")
                .consumes("application/json")
                .produces("application/json")

                .get("/{id}").description("Per user by id").outType(Person.class)
                .param().name("id").type(RestParamType.path)
                .description("The id of the user to get").dataType("int").endParam()
                .to("bean:personService?method=getPerson(${header.id})")

                .put().description("Updates or create a Person").type(Person.class)
                .param().name("body").type(RestParamType.body)
                .description("The person to update or create").endParam()
                .to("bean:personService?method=addPerson(${body})")

                .get("/findAll").description("Find all person").outType(Person[].class)
                .to("bean:personService?method=getPersons");

    }
}
