package com.tavisca.cache.impl;

/**
 * Cache Node
 * 
 * @author Naresh Mahajan
 *
 */
public class CacheNode {
	/**
	 * This Field will be Use as Cache Key
	 */
	private String key;
	/**
	 * This Field will be use as any java Object
	 */
	private Object value;
	/**
	 * Previous Linked Node
	 */
	private CacheNode pre;
	/**
	 * Next Linked Node
	 */
	private CacheNode next;

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public CacheNode(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Returning the Key of Cache Node
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the Key for Cache Node
	 * @param key
	 */
	public void setKey(final String key) {
		this.key = key;
	}

	/**
	 * Returning the Value of Cache Node
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set the Vale for Cache Node
	 * @param value
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * get the previous Linked Node of Current Node
	 * @return
	 */
	public CacheNode getPre() {
		return pre;
	}

	/**
	 * Set the Previous Linked Node of Current Node
	 * @param pre
	 */
	public void setPre(final CacheNode pre) {
		this.pre = pre;
	}

	/**
	 * get the Next Linked Node of Current Node
	 * @return
	 */
	public CacheNode getNext() {
		return next;
	}

	/**
	 * Set the Next Linked Node of Current Node
	 * @param next
	 */
	public void setNext(final CacheNode next) {
		this.next = next;
	}
}
