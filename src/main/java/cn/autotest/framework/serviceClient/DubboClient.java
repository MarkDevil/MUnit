package cn.autotest.framework.serviceClient;

import cn.autotest.framework.Utils.ReflectorHelper;
import cn.autotest.framework.serviceConfig.ServiceConfigItem;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Mark .
 * Data : 2016/7/18
 * Desc :
 */
public class DubboClient implements ClientEngine {
    Logger logger = LoggerFactory.getLogger(DubboClient.class);
    private String zk_addr;
    private String zk_protocol;
    private String zk_group;
    private String interfacename;
    private String group;
    private String method;
    private String version;
    private String protocol;
    RegistryConfig registryConfig = new RegistryConfig();
    ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();


    public DubboClient(ServiceConfigItem item){
        this.zk_addr = item.getZk_addr();
        this.zk_protocol = item.getZk_protocol();
        this.zk_group = item.getZk_group();
        this.interfacename = item.getInterfaceName();
        this.group = item.getGroup();
        this.version = item.getVersion();
        this.method = item.getMethod();
        this.protocol = item.getProtocol();
        //this.interfaceType = item.getInterfaceType();
        registryConfig.setAddress(zk_addr);
        registryConfig.setProtocol(zk_protocol);
        registryConfig.setGroup(zk_group);
        reference.setInterface(interfacename);
        reference.setGroup(group);
        reference.setVersion(version);
        reference.setRegistry(registryConfig);
        reference.setGeneric(true);
        reference.setApplication(new ApplicationConfig(item.getAppname()));
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> param) {
        if (param != null && !param.equals("")){
            String[] paraType;
            try {
                logger.info(String.format("Reflection request : {%s} ,{%s}",interfacename,method));
                paraType = ReflectorHelper.getMethodParamTypes(interfacename.getClass(),method);
                Object[] paramter = new Object[param.size()];
                GenericService genericService = reference.get();
                Object retObj = genericService.$invoke(
                        method,
                        paraType,
                        paramter
                );
                Map<String,Object> retMap =JSON.parseObject(JSONObject.toJSONString(retObj));
                logger.info("Response map ：" +retMap.toString());
                return retMap;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            try {
                throw new RuntimeException(new Throwable(String.format("输入参数不能为空，请检查参数: {%s}",param)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
