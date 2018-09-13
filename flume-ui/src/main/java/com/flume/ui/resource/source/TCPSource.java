package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class TCPSource {
        
    @FlumeResource(name = "type", value = "netcat", mandatory = true)
    private String type;

    @FlumeResource(name = "bind", mandatory = true)
    private String bind;

    @FlumeResource(name = "port", mandatory = true)
    private String port;
	
    @FlumeResource(name = "max-line-length", mandatory = true, value="512")
    private String providerURL;

    @FlumeResource(name = "ack-every-event", mandatory = true, value="true")
    private String ackEveryEvent;
	
    private String interceptor;
	
    private String selector;

}
