package com.course.cases;


import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);

        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);


        //发请求，获取结果
        String result = getResult(addUserCase);

        try {
            // 刷新MySQL数据库表
            session.getConnection().createStatement().execute("FLUSH TABLES");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //验证返回结果
        User user = session.selectOne("addUser",addUserCase);
        System.out.println("user：" + user);
//        System.out.println(user.toString());

        Assert.assertEquals(addUserCase.getExpected(),result);
    }


    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        System.out.println("返回response：" + response);
        System.out.println("返回的response.getEntity()：" + response.getEntity());

        String result;
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("返回的result:" + result);
        return result;
    }
}
