package com.test.cache.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tavisca.cache.exception.ElementNotFoundException;
import com.tavisca.cache.impl.CacheManager;

public class CacheManagerTest {

	static CacheManager cacheManager = null;

	@BeforeClass
	public static void setUpClass() {
		try {
			cacheManager = CacheManager.init(3);
		} catch (AssertionError ae) {
			System.err.println(ae.getMessage());
		}
	}
	
	@Before
	public void init() {
		cacheManager = CacheManager.getInstance();
		cacheManager.flushCache();
	}

	@Test
	public void assertCacheCapicity() {
		assertEquals(Integer.valueOf(3), cacheManager.getCapicity());
	}
	
	@Test
	public void assertCacheSetOperation() throws ElementNotFoundException {
		String value = "Test Cache Set Operation";
		String key = "key_1";
		cacheManager.set(key, value);
		assertEquals(1, cacheManager.getCount());
	}
	
	@Test
	public void assertCacheGetOperation() throws ElementNotFoundException {
		String value = "Test Cache Set Operation";
		String key = "key_1";
		cacheManager.set(key, value);
		assertEquals(value, cacheManager.get(key));
	}
	
	@Test
	public void assertCacheRemoveOperation() throws ElementNotFoundException {
		String value = "Test Cache Set Operation";
		String key = "key_1";
		cacheManager.set(key, value);
		cacheManager.remove(key);
		assertEquals(null, cacheManager.get(key));
	}
	
	@Test
	public void assertCacheFlushOperation() throws ElementNotFoundException {
		cacheManager.set("key_1", "value_1");
		cacheManager.set("key_2", "value_2");
		cacheManager.set("key_3", "value_1");
		cacheManager.set("key_4", "value_4");
		cacheManager.flushCache();
		assertEquals(0, cacheManager.getCount());
	}

	@Test
	public void assertThatLeastUsedItemsIsRemovedIfAnyItemIsNotRequested() throws ElementNotFoundException {
		cacheManager.set("key_1", "value_1");
		cacheManager.set("key_2", "value_2");
		cacheManager.set("key_3", "value_1");
		cacheManager.set("key_4", "value_4");
		assertNull(cacheManager.get("key_1"));
		assertNotNull(cacheManager.get("key_2"));
		assertNotNull(cacheManager.get("key_3"));
		assertNotNull(cacheManager.get("key_4"));
	}
	
	@Test
	public void assertThatLeastUsedItemsIsRemovedIfAnyItemIsRequested() throws ElementNotFoundException {
		cacheManager.set("key_1", "value_1");
		cacheManager.set("key_2", "value_2");
		cacheManager.get("key_1");
		cacheManager.set("key_3", "value_1");
		cacheManager.set("key_4", "value_4");
		
		assertNull(cacheManager.get("key_2"));
		assertNotNull(cacheManager.get("key_1"));
		assertNotNull(cacheManager.get("key_3"));
		assertNotNull(cacheManager.get("key_4"));
	}
}
