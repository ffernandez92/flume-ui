package com.flume.ui.resource.sink;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class HDFSSink {
    
    @FlumeResource(name = "type", value = "hdfs", mandatory = true)
    private String type;

    @FlumeResource(name = "hdfs.path", mandatory = true)
    private String hdfsPath;
    
    @FlumeResource(name = "hdfs.filePrefix")
    private String hdfsFilePrefix;
    
    @FlumeResource(name = "hdfs.fileSuffix")
    private String hdfsFileSuffix;
    
    @FlumeResource(name = "hdfs.inUsePrefix")
    private String hdfsInUsePrefix;
    
    @FlumeResource(name = "hdfs.inUseSuffix", value = ".tmp")
    private String hdfsInUseSuffix;
    
    @FlumeResource(name = "hdfs.rollInterval", value = "30")
    private String hdfsRollInterval;

    @FlumeResource(name = "hdfs.rollSize", value = "1024")
    private String hdfsRollSize;
    
    @FlumeResource(name = "hdfs.rollCount", value = "10")
    private String hdfsRollCount;
    
    @FlumeResource(name = "hdfs.idleTimeout", value = "0")
    private String hdfsIdleTimeout;
    
    @FlumeResource(name = "hdfs.batchSize", value = "100")
    private String hdfsBatchSize;
    
    @FlumeResource(name = "hdfs.codeC")
    private String hdfsCodeC;
    
    @FlumeResource(name = "hdfs.fileType", value = "DataStream")
    private String hdfsFileType;
	
    @FlumeResource(name = "hdfs.maxOpenFiles", value = "5000")
    private String hdfsMaxOpenFiles;
	
    @FlumeResource(name = "hdfs.minBlockReplicas")
    private String hdfsMinBlockReplicas;
    
    @FlumeResource(name = "hdfs.writeFormat")
    private String hdfsWriteFormat;
    
    @FlumeResource(name = "hdfs.callTimeout", value = "10000")
    private String hdfsCallTimeout;
    
    @FlumeResource(name = "hdfs.threadsPoolSize", value = "10")
    private String hdfsThreadsPoolSize;
    
    @FlumeResource(name = "hdfs.rollTimerPoolSize")
    private String hdfsRollTimerPoolSize;
	
    @FlumeResource(name = "hdfs.kerberosPrincipal")
    private String hdfsKerberosPrincipal;
	
    @FlumeResource(name = "hdfs.kerberosKeytab")
    private String hdfsKerberosKeytab;
	
    @FlumeResource(name = "hdfs.proxyUser")
    private String hdfsProxyUser;
	
    @FlumeResource(name = "hdfs.round", value = "false")
    private String hdfsRound;
	
    @FlumeResource(name = "hdfs.roundValue", value = "1")
    private String hdfsRoundValue;
    
    @FlumeResource(name = "hdfs.roundUnit", value = "second")
    private String hdfsRoundUnit;
    
    @FlumeResource(name = "hdfs.timeZone")
    private String hdfsTimeZone;
	
    @FlumeResource(name = "hdfs.useLocalTimeStamp", value = "true")
    private String hdfsUseLocalTimeStamp;
    
    @FlumeResource(name = "hdfs.closeTries")
    private String hdfsCloseTries;
	
    @FlumeResource(name = "hdfs.retryInterval")
    private String hdfsRetryInterval;

    // Mandatory
    private String serializer;

}
