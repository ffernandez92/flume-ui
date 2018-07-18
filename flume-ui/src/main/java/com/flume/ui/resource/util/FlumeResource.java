package com.flume.ui.resource.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlumeResource {
    
	String name();

	String value() default "";

	boolean mandatory() default false;
}

