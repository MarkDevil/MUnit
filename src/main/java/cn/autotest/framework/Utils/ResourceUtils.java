package cn.autotest.framework.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/12
 * Author : mark
 * Desc   :
 */

public class ResourceUtils {

    Logger logger = LoggerFactory.getLogger(ResourceUtils.class);
    static ResourceUtils resourceUtils = new ResourceUtils();



    /**
     * 获取资源路径
     * @return
     */
    public String getResourcePath(Object object){
        String path = object.getClass().getResource("").getPath();
        logger.info("Reources path : {}",path);
        return path;
    }

    /**
     * 根据传入对象获取资源路径
     * @param filename
     * @param object
     * @return
     */
    public String getResourceFilePath(String filename,Object object){
        if (filename == null || filename.equals("")){
            throw new IllegalArgumentException("Input parameter is incorrect");
        }
        String retPath = object.getClass().getResource("" + filename).getPath();
        logger.info("File location : {}",retPath);
        return retPath;
    }

    public String getFullClassName(Class<?> clasz){
        String fullname = clasz.getCanonicalName();
        logger.info(fullname);
        return fullname;
    }

    public static void main(String[] args) {

        resourceUtils.getFullClassName(ResourceUtils.class);
        resourceUtils.getResourcePath(new ResourceUtils());
    }
}
