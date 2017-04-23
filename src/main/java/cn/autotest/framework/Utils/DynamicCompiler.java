package cn.autotest.framework.Utils;

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

/**
 * Created by MingfengMa .
 * Data   : 2017/4/23
 * Author : mark
 * Desc   :
 */

public class DynamicCompiler {

    Logger logger = LoggerFactory.getLogger(DynamicCompiler.class);

    /**
     * 编译指定路径下的java类文件
     * @param writerPath
     */
    public void compile(String writerPath){
        //系统编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = javaCompiler.getStandardFileManager(null,null,null);
        //获取指定路径下的java类文件
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(writerPath);
        //编译任务
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,manager,null,null,null,it);
        task.call();
        try {
            manager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过指定路径加载类文件
     * @param packPath
     * @return
     */
    public Class<?> createInstance(String packPath){
        URL[] urls = null;
        Object object = null;
        try {
            urls = new URL[]{new URL("file:/" + System.getProperty("user.dir") + "/src/")};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class claz = null;
        try {
            claz = urlClassLoader.loadClass(packPath);
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
            method.invoke(claz.newInstance(),parameterTypes.getClass());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
