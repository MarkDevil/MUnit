package cn.autotest.framework.rules;

import cn.autotest.framework.Utils.ThreadUtils;
import cn.autotest.framework.annotation.TestCase;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mark .
 * Data : 2016/9/13
 * Desc :
 */
public class ThreadRule implements TestRule {

    int client_num;
    int thread_num;
    Logger logger = LoggerFactory.getLogger("[Thread rule]");

    @Override
    public Statement apply(final Statement base, final Description description) {
        TestCase testCase = description.getAnnotation(TestCase.class);
        String classname = description.getClassName();
        client_num = testCase.clientnum();
        thread_num = testCase.threadnum();
        if (1 == client_num){
            return base;
        }
        if (classname != null){

            logger.info("Client num : " + client_num + "\t" + "Thread num : " + thread_num);
            ThreadUtils threadUtils = new ThreadUtils(thread_num,client_num);
            try {
                threadUtils.executeThread(Class.forName(classname),description.getMethodName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return base;
    }


}
