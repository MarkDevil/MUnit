package testrunner;

import cn.autotest.framework.Utils.ResourceUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/12
 * Author : mark
 * Desc   :
 */

public class TestResourcesUtils {
    Logger logger = LoggerFactory.getLogger(TestResourcesUtils.class);
    ResourceUtils resourceUtils = new ResourceUtils();

    @Test
    public void testGetPath(){
//        resourceUtils.getResourceFilePath("loan");
        resourceUtils.getResourcePath(new TestResourcesUtils());
    }
}
