package com.course.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupOnMethod {

    @Test(groups = "server")
    public void test1(){
        System.out.println("this is server test method 1111111");
    }

    @Test(groups = "server")
    public void test2(){
        System.out.println("this is server test method 2222222");
    }

    @Test(groups = "client")
    public void test3(){
        System.out.println("this is client test method 3333333");
    }

    @Test(groups = "client")
    public void test4(){
        System.out.println("this is client test method 444444");
    }

    @BeforeGroups("server")
    public void beforeGroupsOnServer(){
        System.out.println("这是服务端组运行之前运行的------");
    }

    @AfterGroups("server")
    public void afterGroupsOnServer(){
        System.out.println("这是服务端组运行之后运行的------");
    }

    @BeforeGroups("client")
    public void beforeGroupsOnClient(){
        System.out.println("这是客户端组运行之前运行的=======");
    }

    @AfterGroups("client")
    public void afterGroupsOnClient(){
        System.out.println("这是客户端组运行之后运行的=======");
    }
}
