package cn.autotest.framework.serviceClient;


import cn.autotest.framework.serviceConfig.ServiceConfigItem;
import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class HttpClientEngine implements ClientEngine {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientEngine.class);
	
	private static final String POST = "POST";
	private static final String GET = "GET";
	private static final String POSTJSON ="JSON";
	private String requestURL = null;
	private String desc = null;
	private String requestMethod = "POST";
	public static long startTime;
	public static long endTime;
	public static long escapseTime;
	public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
	
	public HttpClientEngine(ServiceConfigItem config) {
		requestURL = config.getUrl();
		desc = config.getDesc();
		requestMethod = config.getMethod();
		if (requestMethod != null && requestMethod.trim().length() != 0) {	
			requestMethod = config.getMethod();
		}
	}
	
	public static void checkParameter(ServiceConfigItem config) {
		if (config.getUrl() == null || config.getUrl().trim().length() == 0) {
			throw new RuntimeException("Parameter for http request url is not valid");
		}
		
		if (config.getMethod() !=null && config.getMethod().trim().length() != 0 
				&& config.getMethod().equalsIgnoreCase(POST) && config.getMethod().equalsIgnoreCase(GET)) {
			throw new RuntimeException("HTTP request method is not valid");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> execute(final Map<String, Object> param) {
		if (requestMethod.equalsIgnoreCase(POST)) {
			return doPost(requestURL, param);
		}
		else if (requestMethod.equalsIgnoreCase(GET)) {
			return doGet(requestURL, param);
		}
		else if (requestMethod.equalsIgnoreCase(POSTJSON)){
			return doPostWithJson(requestURL,param);
		}
		else {
			throw new RuntimeException(String.format("HTTP request method [%s] does not support", requestMethod));
		}
	}
	
	public static Map doGet(String url, Map<String, Object> param) {
		HttpClient httpClient = new DefaultHttpClient();
		String queryStr = printMap(param);
		String wholeUrl = url + "?" + queryStr;
		HttpGet httpGet = new HttpGet(wholeUrl);

		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}

			String charset = "UTF-8";
			Header contentTypeHeader = entity.getContentType();
			if (contentTypeHeader != null) {
				String contentType = contentTypeHeader.getValue();
				String charsetReal = getCharset(contentType);
				if (!StringUtils.isEmpty(charsetReal)) {
					charset = charsetReal;
				}
			}

			InputStream stream = entity.getContent();
			StringBuilder strBuilder = new StringBuilder();
			String line = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			while ((line = reader.readLine()) != null) {
				strBuilder.append(line);
			}

			String responseStr = strBuilder.toString();
			logger.info("Http response: " + responseStr);
			Map result = JSON.parseObject(responseStr, Map.class);
			return result;
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	private static String printMap(Map<String, Object> param) {
		if (param == null || param.size() == 0) {
			return "";
		}

		StringBuilder strBuild = new StringBuilder();
		try {
			for (String key : param.keySet()) {
				String value = (String)param.get(key);
				String encode = URLEncoder.encode(value, "UTF-8");
				strBuild.append(key);
				strBuild.append("=");
				strBuild.append(encode);
				strBuild.append("&");
			}

			String queryString = strBuild.toString();
			queryString = queryString.substring(0, queryString.length() - 1);
			return queryString;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	@SuppressWarnings("deprecation")
	public static Map doPost(String url, Map<String, Object> param) {
		if (url == null || url.trim().length() == 0) {
			throw new InvalidParameterException("url is empty");
		}

		if (param == null) {
			throw new InvalidParameterException(
					"param value for post request is null");
		}

		logger.info(String.format("HTTP POST request to %s with parameter %s", url, param.toString()));
		HttpClient httpClient = new DefaultHttpClient();
		String responseContent = "";
		try {
			HttpPost post = new HttpPost(url);
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (String key : param.keySet()) {
				String value = (String)param.get(key);
				NameValuePair nvPair = new BasicNameValuePair(key, value);
				paramList.add(nvPair);
			}

			post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
			startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				escapseTime = System.currentTimeMillis() - startTime;
				logger.info(String.format("[Escapse Time ] : %s ms",escapseTime));
				Header responseHead = responseEntity.getContentType();
				String headStr = "";
				if (responseHead != null) {
					headStr = responseHead.getValue();
				}
				String charSet = getEncoding(headStr);
				InputStream inStream = responseEntity.getContent();
				try {
					StringBuffer sb = new StringBuffer();
					byte[] btemp = new byte[1024];
					int count = inStream.read(btemp);
					while (count >= 0) {
						sb.append(new String(btemp, 0, count, charSet));
						count = inStream.read(btemp);
					}

					responseContent = sb.toString();
				} finally {
					// Closing the input stream will trigger connection release
					try {
						inStream.close();
					} catch (Exception ignore) {
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		try {
			logger.info("Http response: " + responseContent);
			Map mapResult = JSON.parseObject(responseContent, Map.class);
			return mapResult;
		} catch (Exception ex) {
			throw new RuntimeException(responseContent);
		}
	}

	/**
	 * @Author Mark
	 * @Date 2016/8/2
	 * @param
	 * @Description	post json方法
	 *
	 */
	public static Map doPostWithJson(String url,Map<String,Object> param){
		if (url == null || url.trim().length() == 0) {
			throw new InvalidParameterException("url is empty");
		}

		if (param == null) {
			throw new InvalidParameterException(
					"param value for post request is null");
		}
		logger.info(String.format("HTTP POST request to %s with parameter %s", url, param.toString()));
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody body = RequestBody.create(JSONTYPE, com.alibaba.fastjson.JSON.toJSONString(param));
		Request request = new Request.Builder()
				.url(url)
				.header("User-Agent","OkHttp Headers.java")
				.post(body)
				.build();
		try {
			startTime = System.currentTimeMillis();
			Response response = okHttpClient.newCall(request).execute();
			ResponseBody res = response.body();
			if (res != null){
				escapseTime = System.currentTimeMillis() - startTime;
				logger.info(String.format("[Escapse Time ] : %s ms",escapseTime));
				logger.info("Response msg is %s",res);
				String resContent = res.string();
				System.out.println(resContent);
				return JSON.parseObject(resContent,Map.class);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private static String getCharset(String contentType) {
		String charset = null;
		final String CharsetIden = "charset=";
		if (StringUtils.isEmpty(contentType)
				|| contentType.toLowerCase().indexOf(CharsetIden) < 0) {
			return charset;
		}

		int startIndex = contentType.toLowerCase().indexOf(CharsetIden);
		int endIndex = contentType.toLowerCase().indexOf(";", startIndex);
		if (endIndex == -1) {
			endIndex = contentType.length();
		}
		charset = contentType.substring(startIndex + CharsetIden.length(),
				endIndex);
		return charset;
	}

	/**
	 * 从http头里获取编码格式
	 * 
	 * @param header
	 * @return
	 */
	public static String getEncoding(String header) {
		String charset = "UTF-8";
		if (header == null || header.trim().equals(""))
			return charset;

		if (matcher(header, "(charset)\\s?=\\s?(utf-?8)")) {
			charset = "UTF-8";
		} else if (matcher(header, "(charset)\\s?=\\s?(gbk)")) {
			charset = "GBK";
		} else if (matcher(header, "(charset)\\s?=\\s?(gb2312)")) {
			charset = "GB2312";
		}
		return charset;
	}

	public static boolean matcher(String s, String pattern) {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE
				+ Pattern.UNICODE_CASE);
		Matcher matcher = p.matcher(s);
		return matcher.find();
	}


}
