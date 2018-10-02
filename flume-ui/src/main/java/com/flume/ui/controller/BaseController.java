package com.flume.ui.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @PostMapping(value = "/flume/config")
    public void flumeConfig(final HttpServletResponse response, @RequestParam(required = false) String desinfo) throws UnsupportedEncodingException {
	String decodeSource = URLDecoder.decode(desinfo, StandardCharsets.UTF_8.name());
	flmService.getFlumeConfiguration(decodeSource);
	//TODO : Implements the Service Configuration
	//return decodeSource;
    }

}
