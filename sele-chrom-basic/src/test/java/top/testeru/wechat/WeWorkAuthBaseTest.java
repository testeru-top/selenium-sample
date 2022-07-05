/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.wechat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description: 扫码登录基础测试
 * @Version 1.0
 * @create: 2022/6/30 14:54
 */
public class WeWorkAuthBaseTest {
    //cookie文件登录
    @Test
    public void getCookieLoginTest(){
        /**
         *  java.lang.IllegalStateException:
         *  The path to the driver executable The path to the driver executable must be set by the webdriver.chrome.driver system property;
         */
        //1、设置一下全局变量，Chromedriver如果没有配置全局变量则会报错
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        //2、打开Chrome浏览器
        WebDriver webDriver = new ChromeDriver();
        //3、访问企业微信登录页面
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame#index");

        //打开浏览器后网址可能会有跳转，获取最新的url地址
        String url = webDriver.getCurrentUrl();
        //4、读取cookie的yaml文件
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String,Object>>> typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<HashMap<String, Object>> readValue = null;
        try {
            readValue = objectMapper.readValue(Paths.get("cookies.yaml").toFile(), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(readValue);
        readValue.stream()
                //过滤企业微信的cookie
                .filter(cookie -> cookie.get("domain").toString().contains("work.weixin.qq.com"))
                .forEach(cookie -> {
                    //写cookie到浏览器
                    webDriver.manage().addCookie(
                            new Cookie(cookie.get("name").toString(), cookie.get("value").toString()));
                });
        //刷新的时候，浏览器会把新的cookie带到服务器，服务器返回登录后的页面
        webDriver.navigate().refresh();

        //cookie文件进行企业微信登录
        String url2 = webDriver.getCurrentUrl();
        System.out.println(!url2.equals(url)?"登录成功":"登录失败");

    }

    @Test
    public void getCookieYaml(){
        //1、读取cookie的yaml文件
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<HashMap<String,Object>>> typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<HashMap<String, Object>> readValue = null;
        try {
            readValue = objectMapper.readValue(Paths.get("cookies.yaml").toFile(), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(readValue);

        //2、从cookie里面获取时间戳列表 null值忽略
        List<Long> cookieList = new ArrayList<>();
        //获取cookie的时间
        readValue.forEach(stringObjectHashMap -> {
            if(stringObjectHashMap.get("expiry")!=null){
                Long expiry = Long.valueOf(stringObjectHashMap.get("expiry").toString());
                cookieList.add(expiry);
            }
        });
        //3、cookie使用规则
        //获取当前时间戳
        System.out.println(cookieList);
        long now = System.currentTimeMillis();//1656574451000  1659163447000
        System.out.println((cookieList.get(0) - 31536000000L));//获取当前cookie获取的时间戳
        System.out.println((now - (cookieList.get(0) - 31536000000L)));
        if((now - (cookieList.get(0) - 31536000000L))/1000 < 7200){
            System.out.println("直接使用cookie文件即可");
        }else {
            System.out.println("扫码保存cookie文件");
        }


    }

    @Test
    public void weWorkTest(){
        /**
         *  java.lang.IllegalStateException:
         *  The path to the driver executable The path to the driver executable must be set by the webdriver.chrome.driver system property;
         */
        //1、设置一下全局变量，Chromedriver如果没有配置全局变量则会报错
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        //2、打开Chrome浏览器
        WebDriver webDriver = new ChromeDriver();
        //3、访问企业微信登录页面
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame#index");

        //4、扫码过程
        //方式一：强制等待扫码,无论10秒内是否扫码成功都要等待10秒
//        try {
//            sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //方式二：扫码过程
        //打开浏览器后网址可能会有跳转，获取最新的url地址
        String url = webDriver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMinutes(1));
        //一旦扫码完成，url会跳转，跳转后自动停止等待并执行后续的操作
        wait.until(webDriver1 -> !webDriver.getCurrentUrl().equals(url));

        //5、扫码后cookie保存
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
