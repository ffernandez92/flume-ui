package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class AvroSource {
    
    @FlumeResource(name = "type", value = "avro", mandatory = true)
    private String type;
    
    @FlumeResource(name = "bind", mandatory = true)
    private String bind;

    @FlumeResource(name = "port", mandatory = true)
    private String port;
    
    private Integer threads;

    @FlumeResource(name = "compression-type")
    private String compressionType = "none";
    
    private String ssl = "false";
    
    private String keystore;

    @FlumeResource(name = "keystore-password")
    private String keystorePassword;

    @FlumeResource(name = "keystore-type")
    private String keystoreType;
    
    private String excludeProtocols = "SSLv3";
    
    private String ipFilter = "false";
    
    private String ipFilterRules;

    private String interceptor;
    
    private String selector;


}
