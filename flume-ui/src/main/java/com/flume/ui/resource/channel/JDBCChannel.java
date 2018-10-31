package com.flume.ui.resource.channel;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class JDBCChannel {
    
    @FlumeResource(name = "type", value = "jdbc", mandatory = true)
    private String type;
    
    @FlumeResource(name = "db.type")
    private String dbType;
    
    @FlumeResource(name = "driver.class")
    private String driverClass;
    
    @FlumeResource(name = "driver.url")
    private String driverUrl;
    
    @FlumeResource(name = "db.username")
    private String dbUsername;
    
    @FlumeResource(name = "db.password")
    private String dbPassword;
    
    @FlumeResource(name = "connection.properties.file")
    private String connectionPropertiesFile;
    
    @FlumeResource(name = "create.schema", value="true")
    private String createSchema;
    
    @FlumeResource(name = "create.index", value="true")
    private String createIndex;
    
    @FlumeResource(name = "create.foreignkey", value="true")
    private String createForeignKey;
 
    @FlumeResource(name = "transaction.isolation")
    private String transactionIsolation;
    
    @FlumeResource(name = "maximum.connections", value="10")
    private String maximumConnections;
    
    @FlumeResource(name = "maximum.capacity", value="0")
    private String maximumCapacity;
   
    @FlumeResource(name = "sysprop.user.home")
    private String syspropUserHome;

}
