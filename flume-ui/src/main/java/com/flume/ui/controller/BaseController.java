package com.flume.ui.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flume.ui.service.FlumeUiService;
import com.google.gson.Gson;


@Controller
public class BaseController {
    
    private static final String FQN_SOURCE = "com.flume.ui.resource.source.";
    
    private static final String FQN_CHANNEL = "com.flume.ui.resource.channel.";
    
    @Resource
    private Gson gson;
    
    @Resource
    private FlumeUiService flmService;
    
    @GetMapping("/")
    public String home(Map<String, Object> model) throws IOException {
	List<String> sources = flmService.getSourceNames();
	List<String> channels = flmService.getChannelNames();
	model.put("stsources", sources);
	model.put("stchannels", channels);
	return "home";
    }
    
    @ResponseBody
    @PostMapping(value = "/source/modal")
    public String sourceModal(@RequestParam(required = false) String source) throws UnsupportedEncodingException, ClassNotFoundException, IllegalArgumentException,
		IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
	return gson.toJson(flmService.getAttrib(URLDecoder.decode(source, "UTF-8"),FQN_SOURCE));
    }
    
    @ResponseBody
    @PostMapping(value = "/channel/modal")
    public String channelModal(@RequestParam(required = false) String source) throws UnsupportedEncodingException, ClassNotFoundException, IllegalArgumentException,
		IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
	return gson.toJson(flmService.getAttrib(URLDecoder.decode(source, "UTF-8"),FQN_CHANNEL));
    }

}
