package com.tavisca.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Annotation for Enabling the Method to remove the Element from Cache.
 * @author Naresh Mahajan 
 */
public @interface CacheRemove {
    String propertyKey();
}

