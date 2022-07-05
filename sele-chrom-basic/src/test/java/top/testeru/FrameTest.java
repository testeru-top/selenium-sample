/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description:
 * @Version 1.0
 * @create: 2022/7/1 18:11
 */
public class FrameTest {

    public static WebDriver webDriver;
    @BeforeAll
    public static void beforeAll(){
        /**
         *  java.lang.IllegalStateException:
         *  The path to the driver executable The path to the driver executable must be set by the webdriver.chrome.driver system property;
         */
        //1、设置一下全局变量，Chromedriver如果没有配置全局变量则会报错
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");

    }

    @Test
    public void test(){
        //1、设置一下全局变量，Chromedriver如果没有配置全局变量则会报错
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        //2、打开Chrome浏览器
        webDriver = new ChromeDriver();
        webDriver.get("https://tieba.baidu.com/index.html");
        /**
         * int index:用frame的index来定位，第一个是0
         * String nameOrId:2.用name、id来定位
         * WebElement frameElement:4.用WebElement对象来定位
         */
        //id="iframeu6739266_0"
        webDriver.switchTo().frame(webDriver.findElement(By.id("iframeu6739266_0")));
        //class="wrapper_top_12"
        webDriver.findElement(By.className("wrapper_top_12")).click();


    }
}
