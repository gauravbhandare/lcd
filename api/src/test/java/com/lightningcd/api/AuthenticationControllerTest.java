package com.lightningcd.api;


import com.google.gson.Gson;
import com.lightningcd.api.model.Authentication;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class AuthenticationControllerTest {

    private MockMvc mockMvc;
    private Authentication authentication;
    private String jsonString;
    private String username;
    private String password;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private String getJSON(Object obj) {
        Gson gson = new Gson();
        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(obj);
        return json;
    }

    @Before
    public void setUp() throws Exception {
        username = "Michael";
        password = "abcd1234";
        authentication = new Authentication(username, password);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test1CreateUser() throws Exception {
        jsonString = getJSON(authentication);
        System.out.println("JSON String is:" + jsonString);
        ResultActions resultActions = mockMvc.perform(post("/createUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Michael"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test2GetUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/getUser")
                .param("username", "Michael")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("abcd1234"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test3AuthenticateUser() throws Exception {
        jsonString = getJSON(authentication);
        ResultActions resultActions = mockMvc.perform(post("/authenticateUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Michael"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test4CheckFailedLogin() throws Exception {

        Authentication authentication = new Authentication("Michael", "badpassword");
        jsonString = getJSON(authentication);
        ResultActions resultActions = mockMvc.perform(post("/authenticateUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(("Invalid Username or Password")))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void test5LoginWithBadUsername() throws Exception {
        Authentication authentication = new Authentication("Simon", "badpassword");
        jsonString = getJSON(authentication);
        ResultActions resultActions = mockMvc.perform(post("/authenticateUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(("User does not exist")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test6UpdateUser() throws Exception {
        authentication.setPassword("1234abcd");
        jsonString = getJSON(authentication);
        ResultActions resultActions = mockMvc.perform(post("/updateUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Michael"))
                .andDo(MockMvcResultHandlers.print());

    }

    /*
    Negative Test case checking for Non-Existent User should get the message in Authentication Object
     */
    @Test
    public void test7CheckForNonExistentUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/getUser")
                .param("username", "Simon")
                .contentType(contentType))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User does not exist"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test8CheckForDuplicateUser() throws Exception {
        jsonString = getJSON(authentication);
        System.out.println("JSON String is:" + jsonString);
        ResultActions resultActions = mockMvc.perform(post("/createUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("User Already Exist"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test9CheckForUpdatingANonExistentUser() throws Exception {
        Authentication authentication = new Authentication("Simon", "1234abcd");
        jsonString = getJSON(authentication);
        ResultActions resultActions = mockMvc.perform(post("/updateUser")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(("User does not exist")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test910DeleteUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/deleteUser")
                .param("username", "Michael"))
                .andExpect(status().isOk())
                .andExpect(content().string("Michael"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void test911DeleteNonExistentUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/deleteUser")
                .param("username", "simon"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

}
