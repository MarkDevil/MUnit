package testrunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/25
 * Author : mark
 * Desc   :
 */

public class TestAssembleData{

    Logger logger = LoggerFactory.getLogger(TestAssembleData.class);
    Map<String, List<String>> testMap = new HashMap<>();

    @Before
    public void init(){


        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
        List<String> list1 = new ArrayList<>();
        list1.add("3333");
        list1.add("4444");

//        HashMap<String, List<?>> testMap = new HashMap<>();
        testMap.put("userid",list);
        testMap.put("username",list1);
    }

    @Test
    public void testAssemble(){
//        AssembleData.generate(testMap);
        logger.info("动态编译成功");
    }

    @Test
    public void testFailed(){
        Assert.assertTrue(1 != 1);
    }


//    @Test
//    public void testAssembleData(){
//        AssembleData.generate(LoanMapper.class,testMap,String.class);
//    }
}
