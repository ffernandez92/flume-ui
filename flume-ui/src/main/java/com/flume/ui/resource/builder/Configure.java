package com.flume.ui.resource.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Configure {
    
    private static final JsonParser jsonParser = new JsonParser();
    
    private static final String SOURCEB = "source";
    
    private static final String CHANNELB = "channel";
    
    private static final String SINKB = "sink";
    
    private static final String SAVED = "saved";
    
    private static final String CR = "\n";
    
    private static final String DOT = ".";
    
    public JsonObject getJSONFlume(String input) {
      return (JsonObject) jsonParser.parse(input);
    }
    
    public Map<String,List<String>> jsonFlumeNames(JsonObject jConfObj) {
	Map<String,List<String>> result = new HashMap<>();
 	for (Map.Entry<String, JsonElement> entry : jConfObj.entrySet()) {
 	  if (entry.getKey().toLowerCase().contains(SOURCEB) && 
 	      entry.getKey().toLowerCase().contains(SAVED)) {
 	      addElement(result,entry.getKey(),SOURCEB);
 	  } else if ((entry.getKey().toLowerCase().contains(CHANNELB) && !entry.getKey().toLowerCase().contains(SINKB)) && 
              entry.getKey().toLowerCase().contains(SAVED)) {
 	      addElement(result,entry.getKey(),CHANNELB);
 	  } else if (entry.getKey().toLowerCase().contains(SINKB) && 
              entry.getKey().toLowerCase().contains(SAVED)) {
 	      addElement(result,entry.getKey(),SINKB);
           }
 	}
 	return result;
    }
    
    /**
     * Get all possible combination of elements present on Flume configuration
     *  
     * @param elems
     * @param conf
     * @param constantPrefix
     * @param suffix
     */
    public void extractElements(JsonObject elems, StringBuilder conf, String constantPrefix, String suffix) {
	int i = 0;
	for (Map.Entry<String, JsonElement> entry : elems.entrySet()) {
	       if ("serializers".equals(entry.getKey())) {
		   buildConvSelectSerial("serializer", conf, constantPrefix + DOT + suffix + DOT, entry);
	       } else if ("converters".equals(entry.getKey())) {
		   buildConvSelectSerial("converter", conf, constantPrefix + DOT + suffix + DOT, entry);
	       } else if ("interceptors".equals(entry.getKey())) {
		   buildConvSelectSerial("interceptors", conf, constantPrefix + DOT + suffix + DOT, entry); 
	       } else if ("selectors".equals(entry.getKey())) {
		  buildConvSelectSerial("selector", conf, constantPrefix + DOT + suffix + DOT, entry);
	       } else {
		 if (!"".equals(entry.getValue().getAsString())) {
		    if(i == 0) {
		      conf.append(CR);	
		    }
		    conf.append(constantPrefix).append(DOT).append(suffix).append(DOT).append(entry.getKey())
		    .append(" = ").append(entry.getValue().getAsString()).append(CR);
		 } 
	       }
	       i++;
	  }
    }
    
    private void buildConvSelectSerial(String type, StringBuilder conf, String prev, Map.Entry<String, JsonElement> entry) {
       JsonArray jArr = entry.getValue().getAsJsonArray();
       if (type.equalsIgnoreCase("interceptors")) {
	 Map<String, List<String>> intercepts = new HashMap<>();  
	 buildIntercepts(jArr, intercepts);
	 parseIntercepts(type, conf, prev, intercepts);
       } else {
	 for(int i = 0; i < jArr.size(); i++) {
	   String[] innerProp = jArr.get(i).getAsString().split(",");
	   for(int j = 0; j < innerProp.length; j++) {
	     if(!"".equals(innerProp[j])) {
	      conf.append(prev + type + DOT);
	      conf.append(innerProp[j]).append(CR);   
	     }   
	   }
	 }   
       }
       
    }

    private void buildIntercepts(JsonArray jArr, Map<String, List<String>> intercepts) {
	for(int i = 0; i < jArr.size(); i++) {
	   String[] innerProp = jArr.get(i).getAsString().split(";");
	   for(int j = 0; j < innerProp.length; j++) {
	     String[] interC = innerProp[j].split("-\\>");
	     if(interC.length > 1) {
		String[] propIternc = interC[1].split(",");
		for(int k = 0; k < propIternc.length; k++) {
		  addElement(intercepts, propIternc[k], interC[0]); 
		} 
	     }

	   }
	 }
    }

    private void parseIntercepts(String type, StringBuilder conf, String prev, Map<String, List<String>> intercepts) {
	if(!intercepts.isEmpty()) {
	   conf.append(prev + type + " = ");  
	   conf.append(intercepts.keySet().toString().replace("[", "").replace("]", "").replace(",", " ")).append(CR);
	   
	   for (Map.Entry<String, List<String>> props : intercepts.entrySet()) {
	     for(String innP: props.getValue()) {
	      conf.append(prev + type + DOT).append(props.getKey()).append(DOT).append(innP).append(CR);	 
	     }
	   }
	   
	 }
    }
    
    private void addElement(Map<String,List<String>> map, String element, String key) {
      if(map.containsKey(key)) {
	 map.get(key).add(element); 
      } else {
	 List<String> value = new ArrayList<>();
	 value.add(element);
	 map.put(key, value); 
      }
    }
    
    public abstract FlumeConfigModel buildConfiguration(final String name, final String input, String constPrefix, String suffx);

}
