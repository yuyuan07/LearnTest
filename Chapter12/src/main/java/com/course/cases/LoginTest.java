package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;


public class LoginTest {

    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取http url")
    public void beforeTest(){
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }

    @Test(groups = "loginTrue", description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();

        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());

        System.out.println(TestConfig.loginUrl);


        //发送请求，验证结果
        String result = getResult(loginCase);

        Assert.assertEquals(loginCase.getExpected(),result);


    }


    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();

        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());

        System.out.println(TestConfig.loginUrl);

        //发送请求，验证结果
        String result = getResult(loginCase);
        Assert.assertEquals(loginCase.getExpected(),result);
    }



    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        System.out.println("返回的response：" + response);
        System.out.println("返回的response.getEntity()：" + response.getEntity());

        String result;
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("返回的结果result：" + result);

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();

        return result;

    }
}
