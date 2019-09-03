## In Memory Cache

This Memory Cache Application is Developed to Achive Following Things,
 
* Any get calls to the underlying data store should be cached in memory.
* There is a limited size to the cache.
* Most recently used objects should be available in cache and the ones which are not accessed very frequently should be discarded.
* There should be provisions to flush the cache.
* Any update to any record should  update the cache and then the underlying store.(In case the updated object is present in the cache)

This Cache application is Developed based on LRU Cache which have HashMap and Doubly LinkedList, In Which HashMap will hold the keys and address of the Nodes of Doubly LinkedList  And Doubly LinkedList will hold the values of keys.  

We will remove element from bottom and add element on start of LinkedList and whenever any entry is accessed , it will be moved to top. so that recently used entries will be on Top and Least used will be on Bottom.

If count reaches to Capacity then we will remove the node from Bottom.


## Getting Started

This Application have two major Parts,
* Cache Manager (Core Implementation)
* Annotations

### Cache Manager (Core Implementation)

CacheManager is Spring Component which take Capacity as Constructor Parameter. CacheManager is Initialize by Spring Container and Constructor Parameter( Capacity Value) are being fecth from Application properties.  

CacheManager class has core LRU implementation.

You can Simply Inject CacheManager in your Spring application and use cache methods.

Inject Cache Manager ```@Autowired``` annotation and Use CacheManager is your application.  

```java
public class TestService {
    @Autowired
    CacheManager cacheManager;
    
    public void SaveRecord()
    {
    	....
    	cacheManager.set("key", "Value");
    }
    
    public void getRecord()
    {
    	...
    	return cacheManager.get("key");
    }
    
    public void removeRecord()
    {
    	...
    	cacheManager.remove("key");
    }
    
    public void flushCache()
    {
    	...
    	cacheManager.flushCache();
    }
}
```

### Annotations

Following Annotations are good way for Efficient use of Cache in Spring Application.
* Cachable
* CacheSet
* CacheGet
* CacheRemove

### Cachable Annotation
 Cachable Annotation is Useful for cache the Returning Result of method. In Second use the data will fetch from Cache instead Data Store.
 Key attribute is required to use this Annotation.
 
```java
public class TestService {
    
    @Cacheable(key="key_1")
    public void getRecord()
    {
    	...
    	return cacheManager.get("key");
    }
}
```

### CacheSet Annotation
 CacheSet Annotation is Useful to Update the cache, Call to this method always update the Cache with returning Result of method. Key attribute is required to use this Annotation
 
```java
public class TestService {
    
    @CacheSet(key = "key_1")
	public String saveRecord(String value) {
		dataStore = value;
		return dataStore;
	}
}
```
### CacheRemove Annotation
 Cacheremove Annotation is Useful to remove element from the cache. Key attribute is required to use this Annotation
 
```java
public class TestService {
    
   @CacheRemove(key = "key_1")
	public String removeRecord() {
		dataStore = null;
		return dataStore;
	}
}
```

## Authors

 **Naresh Mahajan**
