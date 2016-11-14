package cn.autotest.framework.rules;

import cn.autotest.framework.Utils.JsonUtils;
import cn.autotest.framework.annotation.TestCase;
import com.alibaba.fastjson.JSONObject;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Mark .
 * Data : 2016/9/7
 * Desc :
 */
public class DataInitRule implements org.junit.rules.TestRule {
    static Logger logger = LoggerFactory.getLogger(DataInitRule.class);
    JsonUtils jsonutil = new JsonUtils();
    String classpath = this.getClass().getResource("/").getPath();
    private JSONObject retjsonStr;


    @Override
    public Statement apply(Statement base, Description description) {
        TestCase testCase = description.getAnnotation(TestCase.class);
        assert base!=null;
        String testdata = testCase.initdata();
        if (!testdata.equalsIgnoreCase("")){
            logger.info("initing test data : " + testdata);
            retjsonStr = jsonutil.readJson(classpath + testdata);
            return base;

        }else {
            logger.info("No data were found ,please check in ");
            return base;
        }

    }

    /**
     * 装载测试数据
     * @param claz mapper类型
     * @return
     */
    public Object load(Class claz){
        if (retjsonStr != null && claz != null) try {
            Object beanObj = Class.forName(claz.getName()).newInstance();
            Method[] methods = claz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    logger.info("bean中的方法名: " + method.getName().substring(3).toLowerCase());
                    if (retjsonStr.containsKey(method.getName().substring(3).toLowerCase()))
                        logger.info("bean 中的方法名" + method.getName());
                    method.invoke(beanObj, retjsonStr.getString(method.getName().substring(3).toLowerCase()));
                }
            }
            logger.info("返回的bean文件 ：" + beanObj);

            return beanObj;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }


}
