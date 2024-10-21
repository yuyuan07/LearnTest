package com.course.testng;

import org.testng.annotations.Test;

public class DependTest {
    @Test
    public void test1(){
        System.out.println("test1  run");
        //throw new RuntimeException();
    }

    //依赖前一个方法，前一个方法失败则忽略调用的方法
    @Test(dependsOnMethods = {"test1"})
    public void test2(){
        System.out.println("test2  run");
    }
}
