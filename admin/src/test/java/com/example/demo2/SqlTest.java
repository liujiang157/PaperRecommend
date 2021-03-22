package com.example.demo2;

import com.example.demo2.mapper.UserMapper;
import com.example.demo2.model.User;
import com.example.demo2.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlTest {

    @Autowired
    private WebApplicationContext wac ;


    //伪造一个MVC的环境，伪造的环境不会启动tomcat，
    // 所以测试用例会启动的很快
    private MockMvc mockMvc;

    //在测试之前注册mockmvc
    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Autowired
    private UserService userService;
    @Test
    void testInsert(){

    }
}
