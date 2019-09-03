package com.tavisca.test.cache.annotation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tavisca.cache.CachebleService;
import com.tavisca.cache.impl.CacheManager;
import com.tavisca.test.datastore.IDataStore;
import com.tavisca.test.datastore.StudentDataStore;
import com.tavisca.test.dto.Student;
import com.tavisca.test.service.StudentService;
/**
 * Test Case Class for Annotation Test
 * @author Naresh Mahajan
 *
 */
public class CacheAnnotationsTest {

	CachebleService<Student> cacheTestService;

	static CacheManager cacheManager = null;
	IDataStore<Student> dataStore = new StudentDataStore();

	@BeforeClass
	public static void setUpClass() {
		try {
			cacheManager = CacheManager.init(3);
		} catch (AssertionError ae) {
			System.err.print(ae.getMessage());
		}
	}

	@Before
	public void init() {
		cacheTestService = StudentService.cachableInstance(dataStore);
	}

	private Student createStudent(Integer rollNo) {
		Student student = new Student();
		student.setRollNo(rollNo);
		student.setDepartment("CBSE");
		student.setName("Naresh");
		return student;
	}

	@Test
	public void testCachableAnnotation() {
		Student student = createStudent(1);
		cacheTestService.saveRecord(student);
		Student result = cacheTestService.getRecord(student);
		assertEquals(result.getName(), student.getName());
		assertEquals(result.getDepartment(), student.getDepartment());

	}

	@Test
	public void testCacheSetAnnotation() {
		Student student = createStudent(2);
		cacheTestService.saveRecord(student);
		Student result = cacheTestService.getRecord(student);
		assertEquals(result.getName(), student.getName());
		assertEquals(result.getDepartment(), student.getDepartment());
		student.setDepartment("ICSE");
		cacheTestService.saveRecord(student);
		result = cacheTestService.getRecord(student);
		assertEquals(result.getDepartment(), "ICSE");
	}

	@Test
	public void testCacheRemoveAnnotation() {
		Student student = createStudent(2);
		cacheTestService.saveRecord(student);
		Student result = cacheTestService.getRecord(student);
		assertEquals(result.getName(), student.getName());
		assertEquals(result.getDepartment(), student.getDepartment());
		cacheTestService.removeRecord(student);
		result = cacheTestService.getRecord(student);
		assertEquals(null, result);
	}
}
