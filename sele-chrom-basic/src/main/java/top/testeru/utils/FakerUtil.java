/*
 * @Author: 霍格沃兹测试开发学社-盖盖
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */
package top.testeru.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.PhoneNumber;

import java.util.Locale;

/**
 * @program: selenium-sample
 * @author: testeru.top
 * @description:
 * @Version 1.0
 * @create: 2022/7/2 10:17
 */
public class FakerUtil{
    static Faker faker = new Faker(Locale.CHINA);

    //部门名
    public static String get_teamName(){
        String teamName = faker.company().suffix();
        return teamName;
    }

    //名字
    public static String get_name(){
        String name = faker.name().fullName();
        return name;
    }


    //手机号
    public static String get_phone(){
        long millis = System.currentTimeMillis();
        String s = String.valueOf(millis).substring(2, 10);
        System.out.println(s);
        return s;
//        PhoneNumber phoneNumber = faker.phoneNumber();
//        return phoneNumber.phoneNumber();
    }
    //句子
    public String get_sentence(){
        return faker.lorem().sentence(6);
    }
    //186 12345678


    //随机数
    public int get_num(int max){
        return faker.number().numberBetween(50,max);
    }
}
