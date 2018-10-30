package com.flume.ui.resource.builder;

public class Source extends Element {
    
    private String name;
    
    private String input;
    
    private static final String constPrefix = ".sources";
    
    private String suffx;
    
    public Source(String name, String input, String suffx) {
     this.name = name;
     this.input = input;
     this.suffx = suffx;
    }
    
    public FlumeConfigModel getSource() {
      return buildConfiguration(name,input,constPrefix,suffx);
    }

}
