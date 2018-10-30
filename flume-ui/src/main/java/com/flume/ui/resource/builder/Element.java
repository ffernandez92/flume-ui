package com.flume.ui.resource.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

public class Element extends Configure {
    
    private static final String STORED = "stored";
    
    private static final String SOURCEB = "source";
    
    private static final String CHANNELB = "channel";
    
    private static final String SINKB = "sink";
   
    @Override
    public FlumeConfigModel buildConfiguration(String name, String input, String constPrefix, String suffx) {
	FlumeConfigModel fcm = new FlumeConfigModel();
	String constantPrefix = name + constPrefix;
	
	Map<String, List<String>> chnWSinks = new HashMap<>();
	JsonObject flumeJSON = getJSONFlume(input);
	Map<String,List<String>> mapNames = jsonFlumeNames(flumeJSON);
	StringBuilder channelConf = new StringBuilder();
	StringBuilder sinkConf = new StringBuilder();
	for (Entry<String, List<String>> entr : mapNames.entrySet()) {
	  List<String> jsonKeys = entr.getValue();
	  for(String jk : jsonKeys) {
	     JsonObject innerJSON = flumeJSON.get(jk).
			getAsJsonObject().get(STORED).getAsJsonObject();
	     if (jk.toLowerCase().contains(SOURCEB)) {
	       StringBuilder mainConf = new StringBuilder();
	       String suffix = name + suffx;
	       extractElements(innerJSON,mainConf,constantPrefix, suffix);
	       fcm.setSourcePlainConfig(mainConf);
	       addElements(SOURCEB,fcm,suffix);
	     } else if (jk.toLowerCase().contains(CHANNELB) && !jk.toLowerCase().contains(SINKB)) {
		String suffix = flumeJSON.get(jk).getAsJsonObject().get("channelname").getAsString();
		extractElements(innerJSON,channelConf,constantPrefix, suffix);
		addElements(CHANNELB,fcm,suffix);
	     } else if (jk.toLowerCase().contains(SINKB)){
		String suffix = flumeJSON.get(jk).getAsJsonObject().get("channelname").getAsString();
		extractElements(innerJSON,sinkConf,constantPrefix, jk.toLowerCase().replace("saved", ""));
		if(chnWSinks.containsKey(suffix)) {
		 List<String> lst = chnWSinks.get(suffix);
		 lst.add(jk.toLowerCase().replace("saved", ""));
		 chnWSinks.put(suffix, lst);
		} else {
		 List<String> tmp = new ArrayList<>();
		 tmp.add(jk.toLowerCase().replace("saved", ""));
		 chnWSinks.put(suffix, tmp);
		}
		fcm.setChannelsWithSinks(chnWSinks);
		addElements(SINKB,fcm,suffix);
	     }
	  }
	}
	fcm.setChannelPlainConfig(channelConf);
	fcm.setSinkPlainConfig(sinkConf);
	return fcm;
    }
    
    private void addElements (String type, FlumeConfigModel fcm, String suffix) {
	switch(type) {
	  case "source" :
	   List<String> newList = fcm.getSources();
	   if( newList != null) {
	     newList.add(suffix);
	     fcm.setSources(newList);
	   } else {
             List<String> tmp = new ArrayList<>();
	     tmp.add(suffix);
	     fcm.setSources(tmp);
	   }   
	  break;
	  case "channel":
	   List<String> channels = fcm.getChannels();
	   if( channels != null) {
	    channels.add(suffix);
	    fcm.setChannels(channels);
	   } else {
	    List<String> tmp = new ArrayList<>();
	    tmp.add(suffix);
	    fcm.setChannels(tmp);
	   }   
	  break;
	  case "sink":
	   List<String> sinks = fcm.getSinks();
	   if( sinks != null) {
	     sinks.add(suffix);
	     fcm.setSinks(sinks);
	   } else {
	     List<String> tmp = new ArrayList<>();
	     tmp.add(suffix);
	     fcm.setSinks(tmp);
	  }  
	  break;
	  }
     }

}
