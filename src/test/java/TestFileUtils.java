import cn.autotest.framework.Utils.ExcelUtils;
import cn.autotest.framework.Utils.FileUtils;
import cn.autotest.framework.Utils.ResourceUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by MingfengMa .
 * Data   : 2017/5/3
 * Author : mark
 * Desc   :
 */

public class TestFileUtils {

    Logger logger = LoggerFactory.getLogger(TestFileUtils.class);
    FileUtils fileUtils = new FileUtils();
    BufferedReader bufferedReader = null;
    ResourceUtils resourceUtils = new ResourceUtils();

    @Test
    public void testReadFile() throws IOException {
        bufferedReader = fileUtils.fileReader("/Users/Shared/gitWorkspace/MUnit/src/test/resources/1212");
        String line;
        while ((line = (bufferedReader.readLine()))!=null){
            logger.info(line);
        }
    }

    @Test
    public void testIO(){

    }

    @Test
    public void testExcelFile(){
        ExcelUtils excelUtils = new ExcelUtils();
        String path = excelUtils.createExcel("src/test/resources/","test","test",
                new String[]{"客户名","电话","公司","来源"});
        logger.info("打印路径为 {}",path);
    }
}
