package com.flume.ui.resource.channel;

import com.flume.ui.resource.util.FlumeResource;

import lombok.Data;

@Data
public class KafkaChannel {
    
    @FlumeResource(name = "type", value = "org.apache.flume.channel.kafka.KafkaChannel", mandatory = true)
    private String type;
    
    @FlumeResource(name = "kafka.bootstrap.servers", mandatory = true)
    private String bootstrapServers;
    
    @FlumeResource(name = "kafka.topic", value = "flume-channel")
    private String topic;
    
    @FlumeResource(name = "kafka.consumer.group.id", value = "flume")
    private String groupId;
    
    private String parseAsFlumeEvent = "true";
    
    private String migrateZookeeperOffsets = "true";
    
    private String pollTimeout = "500";
    
    private String defaultPartitionId;
    
    private String partitionIdHeader;
    
    @FlumeResource(name = "kafka.consumer.auto.offset.reset", value = "latest")
    private String kafkaConsumerAutoOffsetReset;
    
    @FlumeResource(name = "kafka.producer.security.protocol", value = "PLAINTEXT")
    private String kafkaProducerSecProtocol;
    
    @FlumeResource(name = "kafka.consumer.security.protocol", value = "PLAINTEXT")
    private String kafkaConsumerSecProtocol;

}
