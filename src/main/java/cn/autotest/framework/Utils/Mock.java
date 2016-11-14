package cn.autotest.framework.Utils;

import cn.autotest.framework.serviceClient.HttpClientEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Mock {
	Logger logger = LoggerFactory.getLogger(Mock.class);
	static String mockAddr = null;
	static {
		GlobalProperty prop = GlobalProperty.getInstance();
		mockAddr = prop.getValue("mock");
	}


	public void hello(){
		System.out.println("hello");
	}
	
	public static void mock(String url, String param) {
		url = mockAddr + url;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("target", "mock");
		String[] keyValueList = param.split(";");
		for(String pair : keyValueList) {
			String[] fields = pair.split("=");
			map.put(fields[0], fields[1]);
		}
		
		Map<String, Object> result = HttpClientEngine.doGet(url, map);
	}
}
