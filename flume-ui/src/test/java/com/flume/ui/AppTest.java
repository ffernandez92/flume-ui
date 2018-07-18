package com.flume.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    private static final String SUFIX_SOURCE = "Source";
    
    private static final String SUFIX_CHANNEL = "Channel";
    
    private static final String SUFIX_SINK = "Sink";

    @Test
    public void testResources() throws IOException {
	System.out.println(getResourcesName("com/flume/ui/resource/source"));
    }
    

    
    public List<String> getResourcesName(final String packageName) throws IOException {
   	final List<String> resourcesNames = new ArrayList<>();
   	final Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName);
   	while (resources.hasMoreElements()) {
   		final URL url = resources.nextElement();
   		final String allClassesString = StringUtils.replace(new Scanner((InputStream) url.getContent()).useDelimiter("\\A").next(), ".class", "");
   		if (StringUtils.isNotEmpty(allClassesString)) {
   			final String[] classesList = allClassesString.split("\n");
   			for (String beanClass : classesList) {
   				if (beanClass.contains(SUFIX_SOURCE)) {
   					resourcesNames.add(StringUtils.replace(beanClass, SUFIX_SOURCE, " " + SUFIX_SOURCE));
   				} else if (beanClass.contains(SUFIX_CHANNEL)) {
   					resourcesNames.add(StringUtils.replace(beanClass, SUFIX_CHANNEL, " " + SUFIX_CHANNEL));
   				} else if (beanClass.contains(SUFIX_SINK)) {
   					resourcesNames.add(StringUtils.replace(beanClass, SUFIX_SINK, " " + SUFIX_SINK));
   				}
   			}
   		}
   	}
   	return resourcesNames;
      }
}
