package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class UDPSource {
    
    @FlumeResource(name = "type", value = "netcatudp", mandatory = true)
    private String type;

    @FlumeResource(name = "bind", mandatory = true)
    private String bind;

    @FlumeResource(name = "port", mandatory = true)
    private String port;
    
    private String remoteAddressHeader;
	
    private String interceptor;
	
    private String selector;

}
