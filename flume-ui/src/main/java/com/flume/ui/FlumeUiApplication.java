package com.flume.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class FlumeUiApplication extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(FlumeUiApplication.class);
    }
    
    public static void main( String[] args ){
	SpringApplication.run(FlumeUiApplication.class, args).registerShutdownHook();
    }
    
}
