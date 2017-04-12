package cn.autotest.framework.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

/**
 * Created by Mark .
 * Data : 2016/9/7
 * Desc :
 */
public class JsonUtils {
    static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * json字符串转
     * @param jsonStr
     * @param entityClass
     * @return
     */

    public Object Json2Entity(String jsonStr, Class entityClass){
        if (jsonStr == null || entityClass == null){
            try {
                throw new Exception("input parameter is incorrect ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return JSON.parseObject(jsonStr,entityClass);
    }


    /**
     * @Author Mark
     * @Date 2016/5/9
     * @param path
     * @Description  读取本地的json文件
     *
     */
    public JSONObject readJson(String path) {
        if ( path!=null && !Objects.equals(path, "")){
            File jsonFile = new File(path);
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();

            try {
                reader = new BufferedReader(new FileReader(jsonFile));
                String tmp;
                while ((tmp = reader.readLine())!= null){
                    data.append(tmp);
                }
            } catch (FileNotFoundException e1){
                try {
                    throw new Exception("File Not found");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            } finally{
                if (reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            JSONObject jsonObject = JSON.parseObject(data.toString());
            logger.info("Json Data ：{} ",jsonObject.toString());
            return jsonObject;

        }else {
            logger.error("The file is not found");
            return null;
        }
    }

    /**
     * @param
     * @Author Mark
     * @Date 2016/9/22
     * @Description 获取指定类所在的路径
     */
    public static String getResourcePath(Class clz) {
        return clz.getResource("/").getPath();
    }

    public static void main(String[] args) {
//        E:\workspace\JUnitClassRunner\target\classes\loan
        new JsonUtils().readJson(JsonUtils.class.getClass().getResource("").getPath() + "loan");
//        logger.info(JsonUtils.class.getClass().getResource("/").getPath() + "loan.json");
    }
}
