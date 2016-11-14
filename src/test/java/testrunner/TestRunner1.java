package testrunner;

import cn.autotest.framework.Utils.JsonUtils;
import cn.autotest.framework.annotation.TestCase;
import cn.autotest.framework.rules.ThreadRule;
import com.alibaba.fastjson.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MingfengMa .
 * Data : 2016/9/6
 * Desc :
 */

public class TestRunner1 {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    JsonUtils jsonUtils = new JsonUtils();
    JSONObject jsonObject = jsonUtils.readJson(JsonUtils.getResourcePath(this.getClass()) + "loan");


    @Rule
    public ThreadRule threadRule = new ThreadRule();


    @Test
    @TestCase(clientnum = 100, threadnum = 2)
    public void testRunner() throws InterruptedException {
        atomicInteger.incrementAndGet();
        logger.info("start test : " + atomicInteger);
        logger.info("userid :" + jsonObject.getString("userid"));
    }
}
