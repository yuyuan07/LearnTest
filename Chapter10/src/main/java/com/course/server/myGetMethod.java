package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@Api(value = "/",description = "这是我全部的get请求方法")
public class myGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到Cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServletRequest 装请求信息的类
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得cookies信息成功";
    }

    /***
     * 要求客户端携带cookies访问
     */
    @RequestMapping(value="/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookies访",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return "这是一个需带cookies信息才能访问的get请求";
        }
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "恭喜你";
            }
        }
        return "这是一个需带cookies信息才能访问的get请求";
    }


    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式 url： key=value&&key=value
     * 我们来模拟获取商品列表
     */
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    @ApiOperation(value = "第一种实现方式 url： key=value&&key=value",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("xie",400);
        myList.put("yifu",500);
        return myList;
    }

    /***
     * 第二种需要携带参数访问的get请求
     * url： ip:port/myGetList/10/20
     */
    @RequestMapping(value = "/myGetList/{start}/{end}")
    @ApiOperation(value = "第二种需要携带参数访问的get请求 ip:port/myGetList/10/2",httpMethod = "GET")
    public Map<String,Integer> myGetList(@PathVariable Integer start,
                                         @PathVariable Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("xie",400);
        myList.put("yifu",500);
        return myList;
    }

}
