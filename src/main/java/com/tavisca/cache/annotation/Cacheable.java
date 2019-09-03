package com.tavisca.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Annotation for Enabling the Method to Cache the Result of Method
 * @author Naresh Mahajan 
 */
public @interface Cacheable {
    String propertyKey();
}
