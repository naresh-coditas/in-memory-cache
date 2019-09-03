
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.tavisca.test.cache.annotation.CacheAnnotationsTest;
import com.test.cache.impl.CacheManagerTest;
 
@RunWith(Suite.class)
 
@Suite.SuiteClasses({
   CacheManagerTest.class,
   CacheAnnotationsTest.class
})
 
public class JunitTestSuite {  
} 