package cn.autotest.framework.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by MingfengMa .
 * Data   : 2017/4/23
 * Author : mark
 * Desc   :
 */

public class FileUtils {
    Logger logger = LoggerFactory.getLogger(FileUtils.class);


    /**
     * 读取指定目录下的文件
     * @param readerPath
     * @return
     */
    public BufferedReader fileReader(String readerPath){
        if (readerPath == null || readerPath.equals("")){
            throw new RuntimeException("file path is incorrect");
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(readerPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    /**
     * 写入文件到指定目录
     * @param bufferedReader
     * @param writePath
     */
    public void fileWriter(BufferedReader bufferedReader,String writePath,String filename){
        String line;
        BufferedWriter bufferedWriter = null;
        try {
            line = bufferedReader.readLine();
            bufferedWriter = new BufferedWriter(new FileWriter(writePath + filename));
            while (line!=null){
                bufferedWriter.write(line + "\r\n");
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    assert bufferedWriter != null;
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
