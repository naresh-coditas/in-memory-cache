package com.tavisca.cache;

/**
 * Interface for Declare classes as Data Store
 * @author Naresh Mahajan
 *
 * @param <T>
 */
public interface IDataStore<T> {
	/**
	 * Method for get record from data store
	 * @param id
	 * @return
	 */
	public T getRecord(Integer id);

	/**
	 * Method for Save record into data store
	 * @param value
	 * @return
	 */
	public  T saveRecord(T value);

	/**
	 * Method for removing record from data store
	 * @param id
	 * @return
	 */
	public T removeRecord(Integer id);
}

