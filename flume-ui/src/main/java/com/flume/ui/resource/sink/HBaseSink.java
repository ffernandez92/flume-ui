package com.flume.ui.resource.sink;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class HBaseSink {
    
    @FlumeResource(name = "type", value = "hbase", mandatory = true)
    private String type;
    
    @FlumeResource(name = "columnFamily", mandatory = true)
    private String columnFamily;
    
    @FlumeResource(name = "table", mandatory = true)
    private String table;
   
    private String zookeeperQuorum;
    
    @FlumeResource(name = "znodeParent",value = "/hbase", mandatory = true)
    private String znodeParent;
    
    private String batchSize = "100";
    
    private String coalesceIncrements = "false";
    
    private String kerberosPrincipal;
    
    private String kerberosKeytab;
    
    private String serializer;

}
