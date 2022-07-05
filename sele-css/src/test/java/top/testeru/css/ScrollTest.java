package top.testeru.css;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description: 滑动示例
 * @Version 1.0
 * @create: 2022/7/5 20:37
 */
public class ScrollTest {
    /**
     * 1、窗口从 (0,0) 滑动 x,y 距离
     * window.scroll(x,y)
     */
    @Test
    public void scrollOneTest(){
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        //窗口固定化
        String moveTo = "window.moveTo(100，100)";
        String scroll = "window.scroll(100,100)";

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(moveTo);


    }
}
