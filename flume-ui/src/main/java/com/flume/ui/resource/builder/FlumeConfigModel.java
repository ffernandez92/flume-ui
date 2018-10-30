package com.flume.ui.resource.builder;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FlumeConfigModel {
    
    private List<String> sources;
    
    private List<String> sinks;
    
    private List<String> channels;
    
    private Map<String, List<String>> channelsWithSinks;
    
    private StringBuilder sourcePlainConfig;
    
    private StringBuilder channelPlainConfig;
    
    private StringBuilder sinkPlainConfig;

}
