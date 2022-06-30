package top.testeru.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Set;

import static java.lang.Thread.sleep;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description: 获取企业微信登录的cookie
 * @Version 1.0
 * @create: 2022/6/29 10:56
 */
public class WeWorkAuthTest {
    static WebDriver webDriver;
    @BeforeAll
    static void beforeAll(){
        //打开Chrome浏览器
        webDriver = new ChromeDriver();
        //1、访问企业微信登录页面
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame#index");
    }
    @AfterAll
    static void afterAll(){
        //关闭、退出浏览器
        webDriver.quit();
    }

    @Test
    void getCookieTest(){
        //打开浏览器后网址可能会有跳转，获取最新的url地址
        String url = webDriver.getCurrentUrl();
        //2、扫码过程
        //方式一：强制等待扫码
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //方式二：扫码过程
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(1000));
//        //一旦扫码完成，url会跳转，跳转后自动停止等待并执行后续的操作
//        wait.until(webDriver -> !webDriver.getCurrentUrl().equals(url));
        //获取扫码后的cookie内容 set集合是一个元素不重复的集合
        Set<Cookie> cookies = webDriver.manage().getCookies();
        //把cookie写入文件，这样什么时候想去拿直接去文件里面拿，不需要去找这个set集合对象
        //写入yaml文件我们需要用到第三方-fasterjson
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            //读取文件是 readValue 写入文件就是writeValue
            objectMapper.writeValue(Path.of("cookies.yaml").toFile(),cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
