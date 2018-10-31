package com.flume.ui.resource.sink;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class LoggerSink {

    @FlumeResource(name = "type", value = "logger", mandatory = true)
    private String type;
    
    @FlumeResource(name = "maxBytesToLog", value = "16")
    private String maxBytesToLog;
    
}
