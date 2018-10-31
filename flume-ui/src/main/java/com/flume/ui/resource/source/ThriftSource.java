package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class ThriftSource {
    
    @FlumeResource(name = "type", value = "thrift", mandatory = true)
    private String type;
    
    @FlumeResource(name = "bind", mandatory = true)
    private String bind;

    @FlumeResource(name = "port", mandatory = true)
    private String port;
    
    private Integer threads;
    
    private String ssl = "false";
    
    private String keystore;

    @FlumeResource(name = "keystore-password")
    private String keystorePassword;

    @FlumeResource(name = "keystore-type")
    private String keystoreType;
    
    private String excludeProtocols = "SSLv3";
    
    private String kerberos = "false";
    
    private String interceptor;
	
    private String selector;

}
