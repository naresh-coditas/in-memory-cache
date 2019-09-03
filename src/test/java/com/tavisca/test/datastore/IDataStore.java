package com.tavisca.test.datastore;

public interface IDataStore<T> {
	public T getRecord(Integer id);

	public  T saveRecord(T value);

	public T removeRecord(Integer id);
}
