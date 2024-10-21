package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;

        String uri = bundle.getString("getCookies.uri");
        String really_url = this.url + uri;

        HttpGet get = new HttpGet(really_url);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");

        System.out.println(result);

        //获取Cookies信息
        this.store = client.getCookieStore();
        List<Cookie> cookieList =  store.getCookies();
        for (Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name = " + name + "; cookie value=" + value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostWithCookies() throws IOException {
        String result;

        String uri = bundle.getString("postWithCookies.uri");
        String really_url = this.url + uri;

        HttpPost post = new HttpPost(really_url);
        DefaultHttpClient client = new DefaultHttpClient();

        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");

        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        client.setCookieStore(this.store);

        HttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //将返回的响应结果字符串转换为json对象
        JSONObject resultJson = new JSONObject(result);
        //判断具体的值
        String success = (String) resultJson.get("huhansan");
        String status = (String) resultJson.get("status");

        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);
    }
}
