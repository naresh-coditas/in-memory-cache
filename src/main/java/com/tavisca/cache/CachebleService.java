package com.tavisca.cache;

/**
 * Interface for Classes to Enable Caching on Service Layer
 * 
 * @author Naresh M
 *
 */
public interface CachebleService<T> {
	/**
	 * Method to get Record from Data Store
	 * @param value
	 * @return
	 */
	T getRecord(T value);

	/**
	 * Method to Save Record to Data Store
	 * @param value
	 * @return
	 */
	T saveRecord(T value);

	/**
	 * Method to remove Record from Data Store
	 * @param value
	 * @return
	 */
	T removeRecord(T value);

	/**
	 * Method to Set Data Store for Service
	 * @param value
	 * @return
	 */
	void setDataStore(IDataStore<T> dataStore);
}
