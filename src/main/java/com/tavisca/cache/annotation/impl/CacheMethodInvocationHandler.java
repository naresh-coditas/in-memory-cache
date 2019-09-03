package com.tavisca.cache.annotation.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

import com.tavisca.cache.annotation.CacheRemove;
import com.tavisca.cache.annotation.CacheSet;
import com.tavisca.cache.annotation.Cacheable;
import com.tavisca.cache.impl.CacheManager;
/**
 * This Class Represents the Enhance the Cache Features using Annotation 
 * and by implementing CacheService
 * This Class Uses the Java Dynamic Proxy Features
 * @author Naresh Mahajan
 *
 */
public class CacheMethodInvocationHandler implements InvocationHandler {
	/**
	 * Cache Enabled Service Object
	 */
	private final Object obj;

	/**
	 * Initialize proxy Method Invocation Handler
	 * @param obj
	 */
	public CacheMethodInvocationHandler(final Object objRef) {
		this.obj = objRef;
	}

	/**
	 * This is Proxy Handler Method called when Proxied Object method get's called.
	 * This Method Uses CacheManager methods for Cache Operation
	 */
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		Object result = null;
		Object annotation = null;
		Method annotaionMethod = null;
		try {
			Class<?>[] paramTypes = new Class<?>[method.getParameterCount()];
			for (int i = 0; i < args.length; i++) {
				paramTypes[i] = args[i].getClass();
			}
			annotation = this.checkCacheAnnotationPresent(method, paramTypes);
			if (annotation != null) {
				annotaionMethod = this.obj.getClass().getMethod(method.getName(), paramTypes);
				result = this.annotationProcess(annotation, annotaionMethod, args);
			} else {
				if ("getRecord".equals(method.getName())) {
					final CacheManager cacheManager = CacheManager.getInstance();
					final String objectKey = obj.getClass().getTypeName();
					result = cacheManager.get(objectKey);
					if (result == null) {
						result = method.invoke(obj, args);
						cacheManager.set(objectKey, result);
					}
				} else if ("saveRecord".equals(method.getName())) {
					final CacheManager cacheManager = CacheManager.getInstance();
					final String objectKey = obj.getClass().getTypeName();
					result = method.invoke(obj, args);
					cacheManager.set(objectKey, result);
				} else if ("removeRecord".equals(method.getName())) {
					final CacheManager cacheManager = CacheManager.getInstance();
					final String objectKey = obj.getClass().getTypeName();
					result = method.invoke(obj, args);
					cacheManager.remove(objectKey);
				}
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} 
		return result;
	}

	/**
	 * This Method Checks the Cache Annotation Presence on Requested method.
	 * @param method
	 * @param types
	 * @return AnnotationObject
	 */
	private Object checkCacheAnnotationPresent(final Method method, final Class<?> ...types) {
		Object result = null;
		try {
			final Method objMethod = this.obj.getClass().getMethod(method.getName(), types);
			if (objMethod.isAnnotationPresent(Cacheable.class)) {
				result = objMethod.getAnnotation(Cacheable.class);
			} else if (objMethod.isAnnotationPresent(CacheSet.class)) {
				result = objMethod.getAnnotation(CacheSet.class);
			} else if (objMethod.isAnnotationPresent(CacheRemove.class)) {
				result = objMethod.getAnnotation(CacheRemove.class);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This Method Uses Cache Manager Functionalities as per Input Annotation Object
	 * @param annotation
	 * @param method
	 * @param args
	 * @return
	 */
	private Object annotationProcess(final Object annotation, final Method method, final Object ... args) {
		Optional<Object> result = Optional.empty();
		try {
			if (annotation instanceof Cacheable) {
				final CacheManager cacheManager = CacheManager.getInstance();
				final Cacheable cacheable = (Cacheable) annotation;
				Object idValue = args[0];
				Optional<String> checkNull = Optional.ofNullable(cacheable.propertyKey()); 
				if (checkNull.isPresent()) {
					String propKey = cacheable.propertyKey();
					PropertyDescriptor pd = new PropertyDescriptor(propKey, args[0].getClass());
					Method getter = pd.getReadMethod();
					idValue = getter.invoke(args[0]);
				}
				String objectKey = obj.getClass().getTypeName().concat("_").concat(String.valueOf(idValue));
				result = Optional.ofNullable(cacheManager.get(objectKey));
				if (!result.isPresent()) {
					result = Optional.ofNullable(method.invoke(obj, args));
					cacheManager.set(objectKey, result.orElse(null));
				}
			} else if (annotation instanceof CacheSet) {
				CacheManager cacheManager = CacheManager.getInstance();
				CacheSet cacheable = (CacheSet) annotation;
				Object idValue = args[0];
				Optional<String> checkNull = Optional.ofNullable(cacheable.propertyKey()); 
				if (checkNull.isPresent()) {
					String propKey = cacheable.propertyKey();
					PropertyDescriptor pd = new PropertyDescriptor(propKey, args[0].getClass());
					Method getter = pd.getReadMethod();
					idValue = getter.invoke(args[0]);
				}
				String objectKey = obj.getClass().getTypeName().concat("_").concat(String.valueOf(idValue));
				result = Optional.ofNullable(method.invoke(obj, args));
				cacheManager.set(objectKey, result.orElse(null));
			} else if (annotation instanceof CacheRemove) {
				CacheManager cacheManager = CacheManager.getInstance();
				CacheRemove cacheable = (CacheRemove) annotation;
				Object idValue = args[0];
				Optional<String> checkNull = Optional.ofNullable(cacheable.propertyKey()); 
				if (checkNull.isPresent()) {
					String propKey = cacheable.propertyKey();
					PropertyDescriptor pd = new PropertyDescriptor(propKey, args[0].getClass());
					Method getter = pd.getReadMethod();
					idValue = getter.invoke(args[0]);
				}
				result = Optional.ofNullable(method.invoke(obj, args));
				String objectKey = obj.getClass().getTypeName().concat("_").concat(String.valueOf(idValue));
				cacheManager.remove(objectKey);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.orElse(null);
	}
}
