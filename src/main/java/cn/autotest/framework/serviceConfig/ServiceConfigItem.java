package cn.autotest.framework.serviceConfig;

import cn.autotest.framework.serviceClient.ProtocolTypeEnum;

public class ServiceConfigItem {
	private ProtocolTypeEnum supportedProtocol;
	private String serviceId;
	private String url;
	private String interfaceName;
	private Class<?> interfaceType;
	private String method;
	private String group;
	private String version;
	private String desc;
	private String zk_addr;
	private String zk_protocol;
	private String zk_group;
	private String protocol;
	private String appname;


	public String getZk_addr() {
		return zk_addr;
	}

	public void setZk_addr(String zk_addr) {
		this.zk_addr = zk_addr;
	}

	public String getZk_protocol() {
		return zk_protocol;
	}

	public void setZk_protol(String zk_protocol) {
		this.zk_protocol = zk_protocol;
	}


	public String getZk_group() {
		return zk_group;
	}

	public void setZk_group(String zk_group) {
		this.zk_group = zk_group;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}
	public ProtocolTypeEnum getSupportedProtocol() {
		return supportedProtocol;
	}

	public void setSupportedProtocol(ProtocolTypeEnum supportedProtocol) {
		this.supportedProtocol = supportedProtocol;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Class<?> getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(Class<?> interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		if (supportedProtocol != null) {
			strBuilder.append("supportedProtocol=" + supportedProtocol.getProtocol() + "&");
		}

		if (serviceId != null) {
			strBuilder.append("serviceId=" + serviceId + "&");
		}

		if (url != null) {
			strBuilder.append("url" + url + "&");
		}

		if (interfaceName != null) {
			strBuilder.append("interface=" + interfaceName + "&");
		}

		if (method != null) {
			strBuilder.append("method=" + method + "&");
		}

		if (group != null) {
			strBuilder.append("group=" + group + "&");
		}

		if (version != null) {
			strBuilder.append("version=" + version + "&");
		}

		if (desc != null) {
			strBuilder.append("desc=" + desc + "&");
		}

		return strBuilder.toString();
	}
}
