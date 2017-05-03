import cn.autotest.framework.Utils.FileUtils;
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
}
