package cn.autotest.framework.Utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class ReflectorHelper {

	static Logger logger = LoggerFactory.getLogger(ReflectorHelper.class);

	public static Method getMethod(Class<?> classObj, String methodName) {
		Method[] allMethods = classObj.getDeclaredMethods();
		Method selectedMethod = null;
		for (Method method : allMethods) {
			if (method.getName().equalsIgnoreCase(methodName)) {
				selectedMethod = method;
				break;
			}
		}
		return selectedMethod;
	}

	public static Map objectToMap(Object obj) {
		String jsonStr = JSON.toJSONString(obj);
		return JSON.parseObject(jsonStr, Map.class);
	}

	public static Class<?> loadClass(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}

	public static <T> T createInstance(Class<T> paramterType, Object value) {
		String jsonStr = JSON.toJSONString(value);
		return JSON.parseObject(jsonStr, paramterType);
	}



	/**
	 * 获取指定函数的入参类型
	 * @param claz			指定类名
	 * @param methodname	指定方法名
	 * @return
	 * @throws ClassNotFoundException
     */

	public static String[] getMethodParamTypes(Class claz ,String methodname) throws ClassNotFoundException {
		if (claz != null && methodname != null){
			logger.info(String.format("The input parameter are {%s} ,{%s}",claz,methodname));
			Class[] params;
			String[] types = null;
			Method[] methods = claz.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodname)) {
					params = method.getParameterTypes();
					types = new String[params.length];
					for (int j = 0; j < params.length; j++) {
						types[j] = params[j].getName();
					}
					break;
				}
			}
			logger.info(String.format("parameters types: {%s}", Arrays.toString(types)));
			return types;
		}else{
			try {
				throw new Exception(new Throwable(String.format("The input parameter is incorrect {%s} ,{%s}",claz,methodname)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			String[] paramtypes = getMethodParamTypes(ReflectorHelper.class,"getMethodParamTypes");
			logger.info(Arrays.toString(paramtypes));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
