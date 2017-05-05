package testrunner;

import cn.autotest.framework.Utils.AssembleData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.autotest.framework.dto.LoanMapper;

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

public class TestAssembleData {

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
    }

    @Test
    public void testAssembleData(){
        AssembleData.generate(LoanMapper.class,testMap,String.class);
    }
}
