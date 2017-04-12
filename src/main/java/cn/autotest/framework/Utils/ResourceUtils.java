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

    /**
     * 读取资源文件夹中的数据
     * @param filename
     * @return
     */
    public String getResourceFilePath(String filename){
        if (filename == null || filename.equals("")){
            throw new IllegalArgumentException("Input parameter is incorrect");
        }
        String retPath = this.getClass().getResource("/" + filename).getPath();
        logger.info("File location : {}",retPath);
        return retPath;
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
        String retPath = object.getClass().getResource("/" + filename).getPath();
        logger.info("File location : {}",retPath);
        return retPath;
    }
}
