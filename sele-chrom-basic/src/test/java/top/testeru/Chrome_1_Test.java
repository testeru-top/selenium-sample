package top.testeru;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.Thread.sleep;

public class Chrome_1_Test {
    static WebDriver driver;
    /**
     * 打开谷歌浏览器
     */
    @Test
    void test1(){

        /**
         *  java.lang.IllegalStateException:
         *  The path to the driver executable The path to the driver executable must be set by the webdriver.chrome.driver system property;
         */
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");

        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);



    }
    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }
}
