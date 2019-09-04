## In Memory Cache(LRU Based)
InMemoryCache is a Java Implementation which provides consistent use for caching solutions. It provides more powerful annotation. 
InMemoryCache application is Developed based on LRU Cache which have HashMap and Doubly LinkedList, In Which HashMap will hold the keys and address of the Nodes of Doubly LinkedList  And Doubly LinkedList will hold the values of keys.  

We will remove element from Tail  and add element on Head of LinkedList and whenever any entry is accessed , it will be moved to Head. so that recently used entries will be on Top(Head) and Least used will be on Bottom(Tail).

If Cache No Of Elements reaches to Max Capacity then Elements will remove from Tail of Linked List.

This Memory Cache Provides Followng Features,
 
* Any get calls to the underlying data store should be cached in memory.
* There is a limited size to the cache.
* Most recently used objects should be available in cache and the ones which are not accessed very frequently should be discarded.
* There should be provisions to flush the cache.
* Any update to any record should  update the cache and then the underlying store.(In case the updated object is present in the cache)

requirements:
* JDK1.8

## Getting Started

This Application have two major Parts,
* Cache Manager (Core Implementation)
* Cache Annotations and CachableService

### Cache Manager (Core Implementation)

CacheManager is Singleton Java class which initialize by init method by passing the Capacity Parameter. This Class Contains the Core LRU Cache Implementaion.

Initalization of Cache Manager and Access for next Use
```java
CacheManager cacheManager = CacheManager.init(3);// 3 is the Max Capacity of Cache Manager
```

Access the Cache Manager Reference for Further Use After Initialization
```java
CacheManager cacheManager = CacheManager.getInstance();
```

Use of Cache Manager Methods
```java
cacheManager.set("UserName", "Naresh Mahajan");
String userName = cacheManager.get("UserName");
cacheManager.remove("UserName");
cacheManager.flushCache();
```

### Cache Annotations And CachebleService

we Can use Cache Manager Effieciently by Implementing the CachebleSerive and by Using Annotations.

InMemoryCache Annotations and CachebleService Implementation done using Java Dynamic Proxy Design Pattern and Reflection API.

we need our service class to implement CachebleSrevice and following static method to defined in that.
```java
public class StudentService implements CachebleService<Student> {
	public static CachebleService<Student> cachableInstance(IDataStore dataStore) {
		StudentService service = new StudentService();
		service.setDataStore(dataStore);
		return (CachebleService<Student>) Proxy.newProxyInstance(service.getClass().getClassLoader(), 
		new Class[] { CachebleService.class }, new CacheMethodInvocationHandler(service));
	}
}
```

Following Annotations are good way for Efficient use of Cache in Java Application.
* Cachable
* CacheSet
* CacheRemove

### Cachable Annotation
 Cachable Annotation is Useful for cache the Returning Result of method. In Second use the data will fetch from Cache instead Data Store.
 propKey attribute is required to use this Annotation.
 
```java
package com.tavisca.service;
public class TestService {
    
    @Cacheable(propKey="rollNo")
    public Student getRecord(String rollNo)
    {
    	...
    	return dataStore.getStudent(rollNo);
    }
}
```
In Above example If Suppose Student record found with XYZ Roll No, then Cache Manager will Store the Store the Student Object with key as "com.tavisca.service_XYZ".

### CacheSet Annotation
 CacheSet Annotation is Useful to Update the cache, Call to this method always update the Cache with returning Result of method. propKey attribute is required to use this Annotation
 
```java
public class TestService {
    
    @CacheSet(propKey = "rollNo")
	public Student saveRecord(Student student) {
		return dataStore.saveRecord(student);
	}
}
```
In Above example If Suppose Student record found with XYZ Roll No, then Cache Manager will update the Record in Cache Manager with key as "com.tavisca.service_XYZ".

### CacheRemove Annotation
 Cacheremove Annotation is Useful to remove element from the cache. Key attribute is required to use this Annotation
 
```java
public class TestService {
    
   @CacheRemove(propKey = "rollNo")
	public Student removeRecord(String rollNo) {
		return dataStore.removeRecord(rollNo);
	}
}
```

## Authors

 **Naresh Mahajan**
