package cn.autotest.framework.Utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

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
     * @param data
     * @return
     */
    public static Map generate(Map<String,List<String>> data){
        Map retmap = new LinkedHashMap();
        if (data.equals("")){
            throw new RuntimeException("Input parameter is incorrect");
        }
        Set<Map.Entry<String, List<String>>> entry = data.entrySet();
        for (Map.Entry<String, List<String>> en : entry) {
            String key = en.getKey();
            List<String> datas = en.getValue();
            String selectedvalue = datas.get(random.nextInt(datas.size()));
            retmap.put(key,selectedvalue);
            logger.debug("select data key is {}, value is {}", en.getKey(), selectedvalue);
        }
        logger.info("返回参数: {}",retmap.toString());
        return retmap;
    }

    /**
     *
     * @param clz
     * @param data
     * @return
     */
    public static Map generate(Class clz,Map<String,List<String>> data,Class<?> paraType){
        Map retmap = new LinkedHashMap();
        Object object = null;
        if (clz.equals("") || data.equals("")){
            throw new RuntimeException("Input parameter is incorrect");
        }
        Set<Map.Entry<String, List<String>>> entry = data.entrySet();
        for (Map.Entry<String,List<String>> en : entry){
            String key = en.getKey();
            List<String> datas = en.getValue();
            String selectedValue = datas.get(random.nextInt(datas.size()));

            String methodname = "set".concat(key.substring(0,1).toUpperCase()+key.substring(1));
            Method method = ReflectionUtils.findMethod(clz,methodname,paraType);
            logger.info("find method : {}",method.toString());
            try {
                object = ReflectionUtils.invokeMethod(method,clz.newInstance(),selectedValue);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            logger.info("调用对象 {}",object);
        }
        return retmap;
    }




}
