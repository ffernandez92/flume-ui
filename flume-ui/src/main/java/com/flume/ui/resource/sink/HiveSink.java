package com.flume.ui.resource.sink;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class HiveSink {

    @FlumeResource(name = "type", value = "hive", mandatory = true)
    private String type;
   
    @FlumeResource(name = "hive.metastore", mandatory = true)
    private String hiveMetastore;
    
    @FlumeResource(name = "hive.database", mandatory = true)
    private String hiveDatabase;
    
    @FlumeResource(name = "hive.table", mandatory = true)
    private String hiveTable;
    
    @FlumeResource(name = "hive.partition")
    private String hivePartition;
    
    @FlumeResource(name = "hive.txnsPerBatchAsk")
    private String hiveTxnsPerBatchAsk = "100";
    
    private String heartBeatInterval = "240";
    
    private String autoCreatePartitions = "true";
    
    private String batchSize = "15000";
    
    private String maxOpenConnections = "500";
    
    private String callTimeout = "10000";
    
    private String roundUnit = "minute";
    
    private String roundValue = "1";
    
    private String timeZone = "LocalTime";
    
    private String useLocalTimeStamp = "false";
    
    private String serializer;
    

}
