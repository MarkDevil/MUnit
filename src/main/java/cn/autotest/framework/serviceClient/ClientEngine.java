package cn.autotest.framework.serviceClient;

import java.util.Map;

public interface ClientEngine {
	//return Map<String, Object> type of response
	Map<String, Object> execute(Map<String, Object> param);
}
