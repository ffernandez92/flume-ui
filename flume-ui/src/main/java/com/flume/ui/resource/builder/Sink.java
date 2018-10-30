package com.flume.ui.resource.builder;

public class Sink extends Element {
    
    private String name;
    
    private String input;
    
    private static final String constPrefix = ".sinks";
    
    private String suffx;
    
    
    public Sink (String name, String input) {
     this.name = name;
     this.input = input;
    }
    
    public FlumeConfigModel getSinks() {
      return buildConfiguration(name,input,constPrefix,suffx);
    }

}
