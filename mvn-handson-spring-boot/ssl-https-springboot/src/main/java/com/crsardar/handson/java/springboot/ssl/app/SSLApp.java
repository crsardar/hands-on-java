package com.crsardar.handson.java.springboot.ssl.app;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Chittaranjan Sardar
 */
@ComponentScan("com.crsardar.handson.java.springboot.ssl")
@SpringBootApplication
public class SSLApp {

    public static void main(String[] args) {

        SpringApplication.run(SSLApp.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {

                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");

                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.addCollection(collection);
                securityConstraint.setUserConstraint("CONFIDENTIAL");

                context.addConstraint(securityConstraint);
            }
        };


        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);

        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector);

        return tomcatServletWebServerFactory;
    }
}
