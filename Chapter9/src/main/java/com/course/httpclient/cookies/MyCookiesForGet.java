package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

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
    public void getWithCookies() throws IOException{
        String result;

        String uri = bundle.getString("getWithCookies.uri");
        String really_url = this.url + uri;

        HttpGet get = new HttpGet(really_url);
        DefaultHttpClient client = new DefaultHttpClient();

        //设置cookies信息
        client.setCookieStore(this.store);

        HttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode:" + statusCode);

        if(statusCode==200){
            result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }


}
