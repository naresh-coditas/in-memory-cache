package com.tavisca.test.datastore;

import java.util.HashMap;
import java.util.Map;

import com.tavisca.cache.IDataStore;
import com.tavisca.test.dto.Student;

public class StudentDataStore implements IDataStore<Student>{

	Map<Integer, Student> map = new HashMap<>();
	
	@Override
	public Student getRecord(Integer id) {
		return map.get(id);
	}

	@Override
	public Student saveRecord(Student value) {
		map.put(value.getRollNo(), value);
		return value;
	}

	@Override
	public Student removeRecord(Integer id) {
		return map.remove(id);
	}
	
}
