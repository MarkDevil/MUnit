import cn.autotest.framework.Utils.DynamicCompiler;
import cn.autotest.framework.Utils.ResourceUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import testrunner.TestAssembleData;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by MingfengMa .
 * Data   : 2017/5/6
 * Author : mark
 * Desc   :
 */

public class TestDynamicComplier {
    Logger logger = LoggerFactory.getLogger(TestDynamicComplier.class);
    DynamicCompiler dynamicCompiler = new DynamicCompiler();
    ResourceUtils resourceUtils = new ResourceUtils();

    @Test
    public void testDynamicComplier() throws IOException {
        dynamicCompiler.compile("/Users/Shared/gitWorkspace/MUnit/src/test/java/testrunner/TestAssembleData.java");
        Class<?> claz = dynamicCompiler.createInstance("testrunner.TestAssembleData");
        Method[] method = ReflectionUtils.getAllDeclaredMethods(claz);

    }

    @Test
    public void testJunitCore(){
        dynamicCompiler.runTest(TestAssembleData.class);
    }
}
