package com.flume.ui.resource.builder;

public class Channel extends Element {
    
    private String name;
    
    private String input;
    
    private static final String constPrefix = ".channels";
    
    private String suffx;
    
    
    public Channel(String name, String input) {
     this.name = name;
     this.input = input;
    }
    
    public FlumeConfigModel getChannel() {
      return buildConfiguration(name,input,constPrefix,suffx);
    }


}
