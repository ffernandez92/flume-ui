package com.flume.ui.resource.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FlumeConfigBuilder {
    
    private static final Logger logger = Logger.getLogger(FlumeConfigBuilder.class.getName());
    
    private static final String SAVED = "saved";
    
    private static final String SOURCEB = "source";
    
    private static final String SQBRKRIGHT = "[";
    
    private static final String SQBRKLEFT = "]";
    
    private static final String CR = "\n";
    
    private static final String CHANEQ = ".channels = ";
    
    private static final String FLUMEPATH = "flume";
    
    private static final FlumeConfigBuilder instance = new FlumeConfigBuilder();
    
    private static final JsonParser jsonParser = new JsonParser();
    
    private FlumeConfigBuilder() {
	
    }
    
    public static FlumeConfigBuilder getInstance() {
       return instance;	
    }
    
    /**
     * Get agent name from input JSON.
     */
    private String getRawAppName(final String input) {
	JsonObject flumeJSON = (JsonObject) jsonParser.parse(input);
	for (Map.Entry<String, JsonElement> entry : flumeJSON.entrySet()) {
	 	  if (entry.getKey().toLowerCase().contains(SOURCEB) && 
	 	      entry.getKey().toLowerCase().contains(SAVED)) {
	 	      return entry.getValue().getAsJsonObject().get("appname").getAsString();
	 	  }
	}
	return null;	
    }
    
    /**
     * Call the {@link #getRawAppName(String)} function and performs 
     * a bracket clean.
     * @param input
     * @return
     */
    private String getCleanAppName(final String input) {
      String rwName = getRawAppName(input);
      if(rwName != null) {
	return rwName.split("\\[")[0];
      } else {
	return null;  
      }
    }
    
    /**
     * Get the min and max number from [x-y] name of
     * the agent.<br>
     * The function calls {@link #getRawAppName(String)} to get the range.
     * <hr>
     * 
     * <b>NOTE:</b> If the min number is 0, it is converted to 1.
     * 
     * @param input <b>JSON String</b>
     * @return List where the first element is the min and the second is the max
     */
    private List<Integer> getMinMax(final String input){
	String name = getRawAppName(input);
	List<Integer> minmax = new ArrayList<>();
	if(name != null && name.contains(SQBRKLEFT) && name.contains("-")) {
	  String numbers = name.split("\\"+SQBRKRIGHT)[1];
	  numbers = numbers.replace(SQBRKLEFT, "");
	  Integer start = Integer.parseInt(numbers.split("-")[0].trim());
	  if(start == 0) {
	    start = 1;    
	  }
	  minmax.add(start);
	  minmax.add(Integer.parseInt(numbers.split("-")[1].trim()));
	} else {
	  minmax.add(1);
	  minmax.add(1);
	}
	return minmax;
    }
    
    /**
     * Entry point to build Flume configuration
     * The function calls two inner functions
     * <br>{@link #getCollector(String, FilePropModel, String, List, StringBuilder, String, Channel)}
     * <br>{@link #getProcessor(String, FilePropModel, String, StringBuilder, Channel)}
     * @param jsonInput
     * @return
     */
    private FilePropModel getConfiguration(final String jsonInput) {
	FilePropModel fpm = new FilePropModel();
	String name = getCleanAppName(jsonInput);
	fpm.setName(name);
	List<Integer> minMax = getMinMax(jsonInput);

	StringBuilder collector = new StringBuilder();
	StringBuilder processor = new StringBuilder();
	StringBuilder sourcesList = new StringBuilder();
	String combo = "flow";

	for (int i = minMax.get(0); i <= minMax.get(1); i++) {
	    sourcesList.append(name + i + combo).append(" ");
	}
	collector.append(name + ".sources = " + sourcesList.toString()).append(CR);
	Channel chn = new Channel(name, jsonInput);
	getCollector(jsonInput, fpm, name, minMax, collector, combo, chn);

	name = name + "processor";
	getProcessor(jsonInput, fpm, name, processor, chn);

	return fpm;
    }

    /**
     * Fills the FilePropModel with the name and collector configuration.
     * @param jsonInput
     * @param fpm
     * @param name
     * @param minMax
     * @param collector
     * @param combo
     * @param chn
     */
    private void getCollector(final String jsonInput, FilePropModel fpm, String name, List<Integer> minMax,
	    StringBuilder collector, String combo, Channel chn) {
	String chnStr = null;
	for (int i = minMax.get(0); i <= minMax.get(1); i++) {
	    Source s = new Source(name, jsonInput, i + "flow");
	    collector.append(s.getSource().getSourcePlainConfig());
	    List<String> lstChans = chn.getChannel().getChannels();
	    if (lstChans != null) {
		chnStr = lstChans.toString().replace(SQBRKRIGHT, "").replace(SQBRKLEFT, "").replace(",", "");
		collector.append(name + ".sources." + name + i + combo + CHANEQ + chnStr);
		collector.append(CR);
	    }
	}
	if (chnStr != null) {
	    collector.append(name + CHANEQ + chnStr).append(CR);
	    collector.append(chn.getChannel().getChannelPlainConfig());
	}
	fpm.setCollector(collector);
    }

    /**
     * Fills the FilePropModel with the processor configuration.
     * @param jsonInput
     * @param fpm
     * @param name
     * @param processor
     * @param chn
     */
    private void getProcessor(final String jsonInput, FilePropModel fpm, String name, StringBuilder processor,
	    Channel chn) {
	String chnStr;
	List<String> lstChans = chn.getChannel().getChannels();
	if (lstChans != null) {
	    String[] sinksNames = lstChans.toString().replace(SQBRKRIGHT, "").replace(SQBRKLEFT, "").trim().split(",");
	    StringBuilder nameSinkSpace = new StringBuilder();
	    StringBuilder headSink = new StringBuilder();

	    Sink k = new Sink(name, jsonInput);
	    Map<String, List<String>> map = k.getSinks().getChannelsWithSinks();
	    if (map != null) {
		for (String snks : sinksNames) {
		    if (map.containsKey(snks.trim())) {
			nameSinkSpace.append(map.get(snks.trim()).toString().replace(SQBRKRIGHT, "")
				.replace(SQBRKLEFT, "").replace(",", "")).append(" ");
			String[] sinksM = map.get(snks.trim()).toString().replace(SQBRKRIGHT, "").replace(SQBRKLEFT, "")
				.split(",");
			for (String sk : sinksM) {
			    headSink.append(name + ".sinks.").append(sk.trim()).append(".channel = ")
				    .append(snks.trim()).append(CR);
			}
		    }
		}

		chnStr = chn.getChannel().getChannels().toString().replace(SQBRKRIGHT, "").replace(SQBRKLEFT, "")
			.replace(",", "");
		processor.append(name + CHANEQ + chnStr).append(CR);
		processor.append(chn.getChannel().getChannelPlainConfig());
		processor.append(CR);
		processor.append(name + ".sinks = " + nameSinkSpace);

		processor.append(k.getSinks().getSinkPlainConfig());
		processor.append(headSink);

		fpm.setProcessor(processor);
	    }
	}
    }
   
   /**
    * Generate the folders and configuration files of the described in
    * the JSON input.
    * <br>
    * It calls the function {@link #getConfiguration(String)} to build the <b>FilePropModel</b>
    * @param input
    * @param flag
    * @return True if the configuration has been generated successfully False otherwise.
    */
   public Boolean writeConfigFile(final String input, Boolean flag) {
       FilePropModel fpm = getConfiguration(input);
       String name = fpm.getName();
       if (name != null) {
	String collectorName = "flume-collector-"+name+"-conf.properties";
	String processorName = "flume-processor-"+name+"-conf.properties";
	
	File f = new File(FLUMEPATH);
	if(flag.equals(Boolean.TRUE) && f.exists() && f.isDirectory()) {
	   deleteDir(f);
           writeConfigFile(input, Boolean.FALSE);
	} else {
	  File collectorPath = new File(FLUMEPATH + "/collector");
	  File processorPath = new File(FLUMEPATH + "/processor");
	  collectorPath.mkdirs();
	  processorPath.mkdirs();
	  try(FileOutputStream foSColl = new 
		    FileOutputStream(FLUMEPATH + "/collector/" + collectorName);
		    FileOutputStream foSProc = new FileOutputStream(FLUMEPATH + "/processor/" + processorName)){  
	    byte[] contentInBytes = fpm.getCollector().toString().getBytes();
	    foSColl.write(contentInBytes);
	    foSColl.flush();
	    
	    StringBuilder confProcessor = fpm.getProcessor();
	    if (confProcessor != null) {
	      byte[] contentProcInBytes = confProcessor.toString().getBytes();
	      foSProc.write(contentProcInBytes);
              foSProc.flush();	
	    }
	    return Boolean.TRUE;
	  } catch (FileNotFoundException e) {
	    logger.info(e.getMessage());
	  } catch (IOException e) {
	    logger.info(e.getMessage());
	  }
	}
      }
      return Boolean.FALSE;
   }
   
   private void deleteDir(File file) {
       File[] contents = file.listFiles();
       if (contents != null) {
           for (File f : contents) {
               deleteDir(f);
           }
       }
       file.delete();
   }
   
   
}
