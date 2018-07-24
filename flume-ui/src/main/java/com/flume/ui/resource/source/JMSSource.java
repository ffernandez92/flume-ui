package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class JMSSource {
    
    @FlumeResource(name = "type", value = "jms", mandatory = true)
    private String type;

    @FlumeResource(name = "initialContextFactory", mandatory = true)
    private String initialContextFactory;

    @FlumeResource(name = "connectionFactory", mandatory = true)
    private String connectionFactory;
	
    @FlumeResource(name = "providerURL", mandatory = true)
    private String providerURL;

    @FlumeResource(name = "destinationName", mandatory = true)
    private String destinationName;

    @FlumeResource(name = "destinationType", mandatory = true)
    private String destinationType;

    private String messageSelector;
	
    private String userName;
	
    private String passwordFile;
	
    private String batchSize = "100";

	
    private String converter;
	
    private String interceptor;
	
    private String selector;

}
