package com.flume.ui.resource.channel;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class MemoryChannel {
    
    @FlumeResource(name = "type", value = "memory", mandatory = true)
    private String type;
    
    @FlumeResource(name = "capacity", value = "100")
    private String capacity;
    
    @FlumeResource(name = "transactionCapacity", value = "100")
    private String transactionCapacity;
    
    @FlumeResource(name = "keep-alive", value = "3")
    private String keepAlive;
    
    private String byteCapacityBufferPercentage = "20";
    
    private String byteCapacity;
    

}
