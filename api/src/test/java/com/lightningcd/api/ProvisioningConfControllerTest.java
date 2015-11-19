package com.lightningcd.api;

import com.google.gson.Gson;
import com.lightningcd.api.model.ProvisionEnv;
import com.lightningcd.api.model.ProvisioningConf;
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
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ProvisioningConfControllerTest {

    private MockMvc mockMvc;
    private ProvisioningConf provisioningConf;
    private String jsonString;
    private String applicationName;


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

        applicationName = "abcdApplication";
        ProvisionEnv provisionEnv1 = new ProvisionEnv();
        provisionEnv1.setEnvironmentName("Dev");
        provisionEnv1.setRestEndPoint("http://qaendpoint.com");
        ProvisionEnv provisionEnv2 = new ProvisionEnv();
        provisionEnv2.setEnvironmentName("Prod");
        provisionEnv2.setRestEndPoint("http://prodendpoint.com");
        ProvisionEnv[] myprovisionEnv = {provisionEnv1, provisionEnv2};
        provisioningConf = new ProvisioningConf(applicationName, myprovisionEnv);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test1CreateProvisioningConf() throws Exception {
        jsonString = getJSON(provisioningConf);
        System.out.println("JSON String is :" + jsonString);
        ResultActions resultActions = mockMvc.perform(post("/createProvisioningConf")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk());


    }

    @Test
    public void test2getProvisioningConf() throws Exception {
        ResultActions actions = mockMvc.perform(get("/getProvisioningConf")
                .param("applicationName", "abcdApplication")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.applicationName").value("abcdApplication"));

    }

    @Test
    public void test3updateProvisioningConf() throws Exception {
        ProvisionEnv provisionEnv1 = new ProvisionEnv();
        provisionEnv1.setEnvironmentName("Dev");
        provisionEnv1.setRestEndPoint("http://devendpoint.com");
        ProvisionEnv provisionEnv2 = new ProvisionEnv();
        provisionEnv2.setEnvironmentName("Prod");
        provisionEnv2.setRestEndPoint("http://prodendpoint.com");
        ProvisionEnv[] myprovisionEnv = {provisionEnv1, provisionEnv2};
        provisioningConf.setRestEndPoint(myprovisionEnv);
        String jsonString = getJSON(provisioningConf);
        System.out.println(jsonString);
        ResultActions actions2 = mockMvc.perform(post("/updateProvisioningConf")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test4deleteProvisioningConf() throws Exception {
        mockMvc.perform(delete("/deleteProvisioningConf")
                .param("applicationName", "abcdApplication"))
                .andExpect(status().isOk())
                .andExpect(content().string("abcdApplication"));

    }
}