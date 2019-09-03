package com.tavisca.cache.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.tavisca.cache.constants.CacheConstants;
import com.tavisca.cache.exception.ElementNotFoundException;

/**
 * This Class Contains Core Implementation of In Memory Cache.
 */
public class CacheManager {

	/**
	 * Singleton Self Instance 
	 */
	private static CacheManager objCacheManager;

	/**
	 * Map for Holding Keys of Cache Element and Address of Cache Nodes.
	 */
	private final Map<String, CacheNode> map;
	/**
	 * No of Elements Inside the Cache
	 */
	private int count;

	/**
	 * Head node of Doubly Linked List
	 */
	private CacheNode head;

	/**
	 * Tail Node of Doubly Linked List
	 */
	private CacheNode tail;

	/**
	 * Variable for Max no of Elements inside the Cache.
	 */
	private final Integer capicity;

	/**
	 * method to get No of Elements Inside the Cache
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * method to get Max no of Elements Inside the Cache
	 * 
	 * @return
	 */
	public Integer getCapicity() {
		return capicity;
	}

	/**
	 * Initialize Cache with Capacity.
	 * 
	 * @param capacity
	 */
	private CacheManager(final Integer capacity) {
		this.capicity = capacity;
		map = new HashMap<>();
		head = new CacheNode("0", 0);
		tail = new CacheNode("0", 0);
		head.setNext(tail);
		tail.setPre(head);
		head.setPre(null);
		tail.setNext(null);
		count = 0;
	}

	/**
	 * Return the Cache Manager Singleton Intance
	 * @return
	 */
	public static CacheManager getInstance() {
		if (objCacheManager == null) {
			throw new AssertionError("Initialization requires.Pleae call init method firt");
		}
		return objCacheManager;
	}

	/**
	 * Initialize the Cache Manager Using Capacity Parameter
	 * @param capacity
	 * @return
	 */
	public static synchronized CacheManager init(final Integer capacity) {
        if (objCacheManager != null)
        {
            throw new AssertionError("Cache already initialized");
        }
		objCacheManager = new CacheManager(capacity);
        return objCacheManager;
    }
	/**
	 * This method removes Input node from Doubly Linked List.
	 * 
	 * @param cacheNode
	 */
	private void deleteCacheNode(final CacheNode cacheNode) {
		cacheNode.getPre().setNext(cacheNode.getNext());
		cacheNode.getNext().setPre(cacheNode.getPre());
	}

	/**
	 * This Method add the Node to Head part of Doubly Linked List.
	 * 
	 * @param cacheNode
	 */
	private void addToHead(final CacheNode cacheNode) {
		cacheNode.setNext(head.getNext());
		cacheNode.getNext().setPre(cacheNode);
		cacheNode.setPre(head);
		head.setNext(cacheNode);
	}

	/**
	 * This method will Find Node Address from Map and removes it from tail
	 * 
	 * @param key
	 * @return
	 * @throws ElementNotFoundException 
	 */
	public Object remove(final String key) throws ElementNotFoundException {
		if (map.containsKey(key)) {
			final Optional<CacheNode> cacheNode = Optional.ofNullable(map.get(key));
			final Object result = cacheNode.orElseThrow(() -> new ElementNotFoundException(CacheConstants.ELM_NOT_FOUND_MSG)).getValue();
			deleteCacheNode(cacheNode.orElseThrow(() -> new ElementNotFoundException(CacheConstants.ELM_NOT_FOUND_MSG)));
			map.remove(key);
			return result;
		}
		return null;
	}

	/**
	 * This method will return the Cache Value and replace that element to Head
	 * Part.
	 * 
	 * @param key
	 * @return
	 * @throws ElementNotFoundException 
	 */
	public Object get(final String key) throws ElementNotFoundException {
		Object result = null;
		if (map.containsKey(key)) {
			final Optional<CacheNode> cacheNode = Optional.ofNullable(map.get(key));
			result = cacheNode.orElseThrow(() -> new ElementNotFoundException(CacheConstants.ELM_NOT_FOUND_MSG)).getValue();
			deleteCacheNode(cacheNode.orElseThrow(() -> new ElementNotFoundException(CacheConstants.ELM_NOT_FOUND_MSG)));
			addToHead(cacheNode.orElseThrow(() -> new ElementNotFoundException(CacheConstants.ELM_NOT_FOUND_MSG)));
		}
		return result;
	}

	/**
	 * This method will add new element or Update Existing One. The Element will
	 * replace with Head Node.
	 * 
	 * @param key
	 * @param value
	 * @throws ElementNotFoundException 
	 */
	public void set(final String key, final Object value) throws ElementNotFoundException {
		if (map.containsKey(key)) {
			final Optional<CacheNode> optionalCacheNode = Optional.ofNullable(map.get(key));
			final CacheNode cacheNode = optionalCacheNode.orElseThrow(() -> new ElementNotFoundException(CacheConstants.ELM_NOT_FOUND_MSG));
			cacheNode.setValue(value);
			deleteCacheNode(cacheNode);
			addToHead(cacheNode);
		} else {
			final CacheNode cacheNode = new CacheNode(key, value);
			map.put(key, cacheNode);
			if (count < capicity) {
				count++;
				addToHead(cacheNode);
			} else {
				map.remove(tail.getPre().getKey());
				deleteCacheNode(tail.getPre());
				addToHead(cacheNode);
			}
		}
	}

	/**
	 * This method will clear the whole Cache and reinitialize again
	 */
	public void flushCache() {
		map.clear();
		head = new CacheNode("0", 0);
		tail = new CacheNode("0", 0);
		head.setNext(tail);
		tail.setPre(head);
		head.setPre(null);
		tail.setNext(null);
		count = 0;
	}
}
