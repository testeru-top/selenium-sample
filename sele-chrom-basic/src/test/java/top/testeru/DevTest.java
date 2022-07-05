/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v101.emulation.Emulation;
import top.testeru.utils.FakerUtil;

import java.util.Optional;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description:
 * @Version 1.0
 * @create: 2022/7/2 17:55
 */
public class DevTest {
    @Test
    void test(){
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");

        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setGeolocationOverride(Optional.of(52.5043),
                Optional.of(13.4501),
                Optional.of(1)));
        driver.get("https://my-location.org/");


    }

    @Test
    public void test1(){
        System.out.println(FakerUtil.get_phone());
    }
}
