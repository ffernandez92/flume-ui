package com.flume.ui.resource.builder;

import lombok.Data;

@Data
public class FilePropModel {
    
    private String name;
    
    private StringBuilder collector;
    
    private StringBuilder processor;

}
