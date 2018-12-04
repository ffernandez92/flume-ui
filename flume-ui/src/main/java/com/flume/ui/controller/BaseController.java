package com.flume.ui.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.flume.ui.service.FlumeUiService;
import com.google.gson.Gson;

/**
 * 
 * @author ffernandez92
 *
 */
@Controller
public class BaseController {
    
    private static final String FQN_SOURCE = "com.flume.ui.resource.source.";
    
    private static final String FQN_CHANNEL = "com.flume.ui.resource.channel.";
    
    private static final String FQN_SINKS = "com.flume.ui.resource.sink.";
    
    @Resource
    private Gson gson;
    
    @Resource
    private FlumeUiService flmService;
    
    @GetMapping("/")
    public String home(Map<String, Object> model) throws IOException {
	List<String> sources = flmService.getSourceNames();
	List<String> channels = flmService.getChannelNames();
	List<String> sinks = flmService.getSinkNames();
	model.put("stsources", sources);
	model.put("stchannels", channels);
	model.put("stsinks", sinks);
	return "home";
    }
    
    @ResponseBody
    @PostMapping(value = "/source/modal")
    public String sourceModal(@RequestParam(required = false) String source) throws UnsupportedEncodingException, ClassNotFoundException, 
		IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
	return gson.toJson(flmService.getAttrib(URLDecoder.decode(source, StandardCharsets.UTF_8.name()),FQN_SOURCE));
    }
    
    @ResponseBody
    @PostMapping(value = "/channel/modal")
    public String channelModal(@RequestParam(required = false) String source) throws UnsupportedEncodingException, ClassNotFoundException, 
		IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
	return gson.toJson(flmService.getAttrib(URLDecoder.decode(source, StandardCharsets.UTF_8.name()),FQN_CHANNEL));
    }
    
    @ResponseBody
    @PostMapping(value = "/sink/modal")
    public String sinkModal(@RequestParam(required = false) String source) throws UnsupportedEncodingException, ClassNotFoundException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
	return gson.toJson(flmService.getAttrib(URLDecoder.decode(source, StandardCharsets.UTF_8.name()),FQN_SINKS));
    }
    
    @ResponseBody
    @PostMapping(value = "/flume/config", produces = "application/zip")
    public void flumeConfig(final HttpServletResponse response, @RequestParam(required = false) String desinfo) throws IOException {
	String decodeSource = URLDecoder.decode(desinfo, StandardCharsets.UTF_8.name());
	if(flmService.getFlumeConfiguration(decodeSource)) {
	  File f = new File("flume");
          File fzip = new File("flume.zip");
          if (f.isDirectory() && f.exists()) {
           response.setHeader("Content-Disposition", "attachment; filename=\"" + "flume" + ".zip\""); 
           final ServletOutputStream sos = response.getOutputStream();
	   sos.write(gettingStream());
	   sos.flush();
	   try {
	    FileUtils.forceDelete(f);
	    FileUtils.forceDelete(fzip);    
	   } catch(Exception e) {
	    
	   }
	   
          }
	}
    }
    
    @ResponseBody
    @PostMapping("/flume/fileupload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
	StringBuilder sb = new StringBuilder();
	if (file.getOriginalFilename().contains(".json")) {
	    BufferedInputStream bin = new BufferedInputStream(file.getInputStream());
	    int b;
	    while ((b = bin.read()) != -1) {
		sb.append("" + Character.toString((char) b));
	    }
	    bin.close();
	} 
	return sb.toString();
    }

    private byte[] gettingStream() {
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
	 try (ZipOutputStream zout = new ZipOutputStream(bos)) {
	     zipSubDirectory("", new File("flume"), zout);
	  }
	    return bos.toByteArray();
          } catch (IOException e) {
	    throw new RuntimeException(e);
        }
    }
    
    private void zipSubDirectory(String basePath, File dir, ZipOutputStream zout) throws IOException {
       byte[] buffer = new byte[4096];
       File[] files = dir.listFiles();
       for (File file : files) {
	 if (file.isDirectory()) {
	  String path = basePath + file.getName() + "/";
	   zout.putNextEntry(new ZipEntry(path));
	   zipSubDirectory(path, file, zout);
	   zout.closeEntry();
	 } else {
	  FileInputStream fin = new FileInputStream(file);
	  zout.putNextEntry(new ZipEntry(basePath + file.getName()));
	  int length;
	  while ((length = fin.read(buffer)) > 0) {
	   zout.write(buffer, 0, length);
	  }
	  zout.closeEntry();
	  fin.close();
	 }
       }
    }

}
