package cn.autotest.framework.Utils;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/23
 * Author : mark
 * Desc   :
 */

public class DynamicCompiler {

    Logger logger = LoggerFactory.getLogger(DynamicCompiler.class);
    ResourceUtils resourceUtils = new ResourceUtils();

    /**
     * 编译指定路径下的java类文件
     * @param writerPath
     */
    public void compile(String writerPath) throws IOException {
        //系统编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = javaCompiler.getStandardFileManager(null,null,null);
        //获取指定路径下的java类文件
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(writerPath);
        //编译任务
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,manager,null, Arrays.asList("-d", "/Users/Shared/gitWorkspace/MUnit/src"),null,it);
        boolean flag = task.call();
        logger.info("[Complie] complie status is {}",flag);
        try {
            manager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过指定路径加载类文件
     * @param classname
     * @return
     */
    public Class<?> createInstance(String classname){
        URL[] urls = null;
        Object object = null;
        try {
            urls = new URL[]{new URL("file:///Users/Shared/gitWorkspace/MUnit/src/testrunner/")};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assert urls != null;
        logger.info("print class path : {}",urls[0].getPath());
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class claz = null;
        try {
            claz = urlClassLoader.loadClass(classname);
//            object = claz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return claz;
    }

    /**
     * 反射执行指定方法
     * @param claz
     * @param methodName
     * @param parameterTypes
     */
    public void invoke(Class<?> claz,String methodName,Class<?>... parameterTypes){
        try {
            java.lang.reflect.Method method = claz.getDeclaredMethod(methodName,parameterTypes);
            method.invoke(claz.newInstance(),parameterTypes);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * junitcore执行指定测试用例
     * @param classes
     * @return
     */
    public Result runTest(Class<?>... classes){
        Result result = JUnitCore.runClasses(classes);
        for (Failure failure:result.getFailures()){
            logger.info("Test failed : {}",failure.getException().toString());
        }
        if (result.wasSuccessful()){
            logger.info("Test case successfully");
        }
        return result;
    }

}
