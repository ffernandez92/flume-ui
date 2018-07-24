package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class HTTPSource {
    
    @FlumeResource(name = "type", value = "http", mandatory = true)
    private String type = "http";
	
    @FlumeResource(name = "port", mandatory = true)
    private String port;
	
    private String bind = "0.0.0.0";
	
    private String handler = "org.apache.flume.source.http.JSONHandler";
	
    private String enableSSL = "false";
	
    private String keystore;
	
    private String keystorePassword;
	
    private String selector;

    private String interceptor;

}
