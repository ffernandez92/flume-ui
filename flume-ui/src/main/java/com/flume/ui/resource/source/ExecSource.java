package com.flume.ui.resource.source;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class ExecSource {
    
    @FlumeResource(name = "type", value = "exec", mandatory = true)
    private String type;
    
    @FlumeResource(name = "command", mandatory = true)
    private String command;
    
    private String shell;
    
    private String restartThrottle = "10000";
    
    private String restart = "false";
    
    private String logStdErr = "false";
    
    private String batchSize = "20";
    
    private String batchTimeout = "3000";
    
    private String interceptor;
	
    private String selector;

}
