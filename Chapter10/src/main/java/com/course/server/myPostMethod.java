package com.course.server;

import com.course.bean.User;
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
@Api(value = "/",description = "这是我全部的post请求方法")
@RequestMapping("/v1")
public class myPostMethod {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后获取Cookies信息",httpMethod = "POST")
    public String login(@RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "pwd",required = true) String pwd,
                        HttpServletResponse response){
        if(userName.equals("zhangsan")&&pwd.equals("123456")){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            System.out.println("恭喜你登录成功，获得cookies");
        }
        return "用户名或者密码错误";
    }


    @RequestMapping(value = "/userList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String userList(HttpServletRequest request,
                         @RequestBody User u){
        Cookie[] cookies = request.getCookies();
//        if(Objects.isNull(cookies)){
//            return "这是一个需要携带cookies信息才能访问到的post请求";
//        }
        User user;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && u.getName().equals("zhangsan")
                    && u.getPwd().equals("123456")){
                user = new User();
                user.setName("lisi");
                user.setAge(23);
                user.setSex("man");
                return user.toString();
            }
        }
        return "参数不合法";
    }

}
