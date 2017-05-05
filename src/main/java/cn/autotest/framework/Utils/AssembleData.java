package cn.autotest.framework.Utils;


import cn.autotest.framework.dto.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/25
 * Author : mark
 * Desc   :
 */

public class AssembleData {

    public static Logger logger = LoggerFactory.getLogger(AssembleData.class);
    public static Random random = new Random();
    static Map<String, List<String>> testMap = new HashMap<>();

    /**
     * 随机选取数据
     *
     * @param loanMapper
     * @param data
     * @param stringClass
     * @return
     */
//    public static Map generate(LoanMapper loanMapper, Map<String, List<String>> data, Class<String> stringClass){
//        Map retmap = new LinkedHashMap();
//        if (data.equals("")){
//            throw new RuntimeException("Input parameter is incorrect");
//        }
//        Set<Map.Entry<String, List<String>>> entry = data.entrySet();
//        for (Map.Entry<String, List<String>> en : entry) {
//            String key = en.getKey();
//            List<String> datas = en.getValue();
//            String selectedvalue = datas.get(random.nextInt(datas.size()));
//            retmap.put(key,selectedvalue);
//            logger.debug("select data key is {}, value is {}", en.getKey(), selectedvalue);
//        }
//        logger.info("返回参数: {}",retmap.toString());
//        return retmap;
//    }

    /**
     *
     * @param obj
     * @param data
     * @return
     */
    public static <T> Map generate(T obj, Map<String,List<String>> data, Class<?>... paraType){
        Map retmap = new LinkedHashMap();

        if (obj.equals("") || data.equals("")){
            throw new RuntimeException("Input parameter is incorrect");
        }
        Set<Map.Entry<String, List<String>>> entry = data.entrySet();
        for (Map.Entry<String,List<String>> en : entry){
            String key = en.getKey();
            List<String> datas = en.getValue();
            String selectedValue = datas.get(random.nextInt(datas.size()));

            String setmethodname = "set".concat(key.substring(0,1).toUpperCase()+key.substring(1));
            String getmethodname = "get".concat(key.substring(0,1).toUpperCase().concat(key.substring(1)));
            logger.info("setmentname : {} , getmethodname : {}",setmethodname,getmethodname);
            Method setmethod = ReflectionUtils.findMethod(obj.getClass(),setmethodname,paraType);
            Method getmethod = ReflectionUtils.findMethod(obj.getClass(),getmethodname);


            try {
                logger.debug("find method : {},selectedvalue : {} ,class instance : {}",
                        setmethod.toString(),selectedValue,obj.toString());
//                object = ReflectionUtils.invokeMethod(setmethod,clz.newInstance(),selectedValue);

                setmethod.invoke(obj,selectedValue);
                Object object = getmethod.invoke(obj);
                logger.info("调用对象 {}",object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return retmap;
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        List<String> list1 = new ArrayList<>();
        list1.add("mark");
        list1.add("jack");
        list1.add("lucy");

//        HashMap<String, List<?>> testMap = new HashMap<>();
        testMap.put("userid",list);
        testMap.put("username",list1);
        for (int i = 0 ;i<100;i++){
            AssembleData.generate(new LoanMapper(),testMap,String.class);
        }

    }



}
