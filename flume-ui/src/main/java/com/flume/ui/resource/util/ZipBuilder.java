package com.flume.ui.resource.util;

public class ZipBuilder {
    
    private static final ZipBuilder instance = new ZipBuilder();
    
    private ZipBuilder() {
	
    }
    
    public ZipBuilder getInstance() {
	return instance;
    }
    
    

}
