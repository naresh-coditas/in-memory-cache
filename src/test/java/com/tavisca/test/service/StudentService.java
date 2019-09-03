package com.tavisca.test.service;

import java.lang.reflect.Proxy;

import com.tavisca.cache.CachebleService;
import com.tavisca.cache.IDataStore;
import com.tavisca.cache.annotation.CacheRemove;
import com.tavisca.cache.annotation.CacheSet;
import com.tavisca.cache.annotation.Cacheable;
import com.tavisca.cache.annotation.impl.CacheMethodInvocationHandler;
import com.tavisca.test.datastore.StudentDataStore;
import com.tavisca.test.dto.Student;
public class StudentService implements CachebleService<Student> {

	private StudentDataStore studentDataStore;

	@Cacheable(propertyKey = "rollNo")
	public Student getRecord(Student value) {
		Student result = studentDataStore.getRecord(value.getRollNo());
		return result;
	}

	@CacheSet(propertyKey = "rollNo")
	public Student saveRecord(Student value) {
		return studentDataStore.saveRecord(value);
	}

	@CacheRemove(propertyKey = "rollNo")
	public Student removeRecord(Student value) {
		return studentDataStore.removeRecord(value.getRollNo());
	}

	public void setDataStore(IDataStore<Student> dataStore) {
		this.studentDataStore = (StudentDataStore) dataStore;
		
	}
	
	@SuppressWarnings("unchecked")
	public static CachebleService<Student> cachableInstance(IDataStore dataStore) {
		StudentService service = new StudentService();
		service.setDataStore(dataStore);
		return (CachebleService<Student>) Proxy.newProxyInstance(service.getClass().getClassLoader(), new Class[] { CachebleService.class },
				new CacheMethodInvocationHandler(service));
	}
}
