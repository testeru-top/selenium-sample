package top.testeru;

import ch.qos.logback.core.util.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {
    String CHROM_PATH = "/Applications/Google Chrome.app/Contents/Frameworks/Google Chrome Framework.framework/Versions";

    //获取浏览器的版本
    @Test
    void test1(){
        //判断路径文件是否存在
//        File file = Paths.get(CHROM_PATH).toFile();
        File file = new File(CHROM_PATH);
        System.out.println(file.exists());
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("浏览器未安装");
        } else {
            System.out.println("浏览器已安装");
            File[] tempList = file.listFiles();
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                }
                if (tempList[i].isDirectory()) {
                    System.out.println("文件夹：" + tempList[i]);
                    Path fileName = tempList[i].toPath().getFileName();
                    if(fileName.toString().contains(".")){
                        System.out.println(fileName);//版本号
                        System.out.println();
                        //https://chromedriver.storage.googleapis.com/index.html?path=102.0.5005.61/
                    }
                }
            }
        }
    }

}
