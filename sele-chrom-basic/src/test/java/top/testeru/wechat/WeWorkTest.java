package top.testeru.wechat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description: 元素相关操作
 * @Version 1.0
 * @create: 2022/6/30 17:56
 */
public class WeWorkTest {
    public static WebDriver webDriver;
    @BeforeAll
    public static void beforeAll(){
        /**
         *  java.lang.IllegalStateException:
         *  The path to the driver executable The path to the driver executable must be set by the webdriver.chrome.driver system property;
         */
        //1、设置一下全局变量，Chromedriver如果没有配置全局变量则会报错
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        //2、打开Chrome浏览器
        webDriver = new ChromeDriver();
    }


    @Test
    public void test(){
        cookieLogin();
        //企业微信cookie登录后，点击通讯录元素
        //1、查找通讯录元素 webDriver.findElement(By.元素定位方式())
        WebElement webElement = webDriver.findElement(By.id("menu_contacts"));
        //2、通讯录元素操作 -- 点击

        webElement.click();
        By addMemberButton = By.linkText("添加成员");

        /**
         * 添加成员页面有3个，2个显示的，1个没有的元素
         * 2个显示的：第1个，是最上的按钮
         * 第2个如果成员过多，则需要翻页
         */
        //最多等待20秒 每隔2s轮询一次，轮询条件为util
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addMemberButton));
        webDriver.findElement(addMemberButton).click();
        webDriver.findElement(By.name("username")).sendKeys("202207031533");
        webDriver.findElement(By.name("acctid")).sendKeys("202207031533");
        webDriver.findElement(By.name("biz_mail")).sendKeys(String.valueOf(System.currentTimeMillis()));
        webDriver.findElement(By.name("mobile")).sendKeys("18612725124");
        webDriver.findElement(By.linkText("保存")).click();
        webDriver.navigate().refresh();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    void cookieLogin(){

        //3、访问企业微信登录页面
        //使用 HTTP POST 操作完成的，该方法将阻塞，直到加载完成
//        webDriver.navigate().to("https://work.weixin.qq.com/wework_admin/frame#index");
        webDriver.get("https://work.weixin.qq.com/wework_admin/frame#index");
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //4、打开浏览器后网址可能会有跳转，获取最新的url地址
        String url = webDriver.getCurrentUrl();
        //5、`cookie`判断
        //5.1、获取cookie.yaml的失效时间
        List<HashMap<String, Object>> cookies = readCookieYaml();
        List<Long> cookieList = new ArrayList<>();
        //获取cookie的失效时间
        cookies.forEach(cookie -> {
            if(cookie.get("expiry")!=null){
                Long expiry = Long.valueOf(cookie.get("expiry").toString());
                cookieList.add(expiry);
            }
        });

        //cookie获取的时间戳
        long cookieMills = cookieList.get(0) - 31536000000L;
        //获取当前时间戳
        long now = System.currentTimeMillis();
        if((now - cookieMills)/1000 < 7200){
            System.out.println("直接使用cookie文件-不需要保存cookie");
        }else {
            System.out.println("扫码保存cookie文件");
            //6、扫码过程 7、扫码后`cookie`保存到本地`yaml`文件
            saveCookie(webDriver, url);
            //8、扫码后要重新加载`cookie`文件
            cookies = readCookieYaml();

        }
        cookies.stream()
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


    private List<HashMap<String, Object>> readCookieYaml() {
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
        return readValue;
    }
    private void saveCookie(WebDriver webDriver, String url) {

        //4、扫码过程
        //方式一：强制等待扫码,无论10秒内是否扫码成功都要等待10秒
//        try {
//            sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        //方式二：扫码过程
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
