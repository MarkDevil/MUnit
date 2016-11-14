package testrunner;

import cn.autotest.framework.annotation.TestCase;
import cn.autotest.framework.rules.DataInitRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mark .
 * Data : 2016/9/7
 * Desc :
 */
public class TestRuleMethod {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    LoanMapper loanMapper;
    String userid;
    String username;

    @Rule
    public DataInitRule testRule = new DataInitRule();

    //    @Rule
//    public ThreadRule threadRule = new ThreadRule();
    @Test
    @TestCase(initdata = "loan")
    public void init() {
        loanMapper = (LoanMapper) testRule.load(LoanMapper.class);
        username = loanMapper.getUsername();
        logger.info("username" + loanMapper.getUsername());
    }


    @Test()
    @TestCase(initdata = "loan")
    public void testrule1(){
        logger.info("username" + loanMapper.getUsername());

    }

    @Test
    @TestCase(initdata = "loan")
    public void test1(){
        int ret = atomicInteger.incrementAndGet();
        logger.info("test " + ret);
    }

}
