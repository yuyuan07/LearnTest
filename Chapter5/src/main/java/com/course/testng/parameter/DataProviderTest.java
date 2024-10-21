package com.course.testng.parameter;

import javafx.scene.chart.PieChart;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1111方法 name="+name+"; age="+age);
    }
    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2222方法 name="+name+"; age="+age);
    }

    @DataProvider(name="methodData")
    public Object[][] methodDataTest(Method method){
        Object[][] result=null;
        if(method.getName().equals("test1")){
            result = new Object[][]{
                    {"jfq",28},
                    {"yuyuan",27}
            };
        }else if(method.getName().equals("test2")){
            result = new Object[][]{
                    {"hyd",27} ,
                    {"sxy",26}
            };
        }
        return result;
    }




    @Test(dataProvider = "data")
    public void testDataProvider(String name,int age){
        System.out.println("name="+ name + "; age="+ age);
    }

    @DataProvider(name="data")
    public Object[][] providerData(){
        Object[][] o = new Object[][]{
                {"zhangsan",10},
                {"lisi",20},
                {"wangwu",30}
        };
        return o;
    }

}
