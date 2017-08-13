package com.rangeanxiety.app;

import com.rangeanxiety.app.service.Network;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public InitializingBean init(Network network) {
        // Starts the initialization of the routing network
        // Please provide file 'Jordan.osm.pbf' on the working directory
        return network::readOSMFile;
    }

}
