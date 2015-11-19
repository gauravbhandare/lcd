package com.lightningcd.api;

import com.google.gson.Gson;
import com.lightningcd.api.model.Component;
import com.lightningcd.api.model.DeployApplication;
import com.lightningcd.api.model.Environment;
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

public class DeploymentControllerTest {

    private MockMvc mockMvc;
    private DeployApplication deployApplication = new DeployApplication();
    String jsonString = "";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        Environment environment = new Environment();
        Component component = new Component();
        environment.setEnvironmentName("QA");
        component.setComponentName("abcdweb");
        component.setComponentType("web");
        component.setComponentVersion("1.0.0.0");
        component.setCpu("1.0");
        component.setMemory("512M");
        Environment[] env = {environment};
        Component[] comp = {component};
        deployApplication.setApplicationName("abcdApplication");
        deployApplication.setEnvironments(env);
        deployApplication.setComponent(comp);
        deployApplication.setProvisioningTypes("ssh");
        this.mockMvc = webAppContextSetup(webApplicationContext).build();


    }

    private String getJSON(Object obj) {
        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(obj);
        return json;
    }

    @Test
    public void Test1CreateApplication() throws Exception {
        System.out.println("Enter-com.lightningcd.api.DeploymentControllerTest.Test1CreateApplication");
        jsonString = getJSON(deployApplication);
        System.out.println(jsonString);
        mockMvc.perform(post("/createApplication")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk());

        ResultActions actions2 = mockMvc.perform(get("/getObjectIdfromApplicationName")
                .param("applicationName", "abcdApplication")
                .contentType(contentType));
        System.out.println(actions2.andReturn().getResponse().getContentAsString());
        System.out.println("Exit-com.lightningcd.api.DeploymentControllerTest.Test1CreateApplication");
    }

    @Test
    public void Test2GetApplication() throws Exception {
        System.out.println("Enter-com.lightningcd.api.DeploymentControllerTest.Test2GetApplication");
        ResultActions actions = mockMvc.perform(get("/getApplication")
                .param("applicationName", "abcdApplication")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.applicationName").value("abcdApplication"));
        ResultActions actions2 = mockMvc.perform(get("/getObjectIdfromApplicationName")
                .param("applicationName", "abcdApplication")
                .contentType(contentType));
        System.out.println(actions2.andReturn().getResponse().getContentAsString());
        System.out.println("Exit-com.lightningcd.api.DeploymentControllerTest.Test2GetApplication");
    }


    @Test
    public void Test3UpdateApplication() throws Exception {
        System.out.println("Enter-com.lightningcd.api.DeploymentControllerTest.Test3UpdateApplication");
        deployApplication.setProvisioningTypes("kubernetes");
        String jsonString = getJSON(deployApplication);
        System.out.println(jsonString);
        ResultActions actions2 = mockMvc.perform(post("/updateApplication")
                .contentType(contentType)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        ResultActions actions3 = mockMvc.perform(get("/getApplication")
                .param("applicationName", "abcdApplication")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.provisioningTypes").value("kubernetes"));
        System.out.println("Exit-com.lightningcd.api.DeploymentControllerTest.Test3UpdateApplication");

    }

    @Test
    public void Test4DeleteApplication() throws Exception {
        System.out.println("Enter-com.lightningcd.api.DeploymentControllerTest.Test4DeleteApplication");
        mockMvc.perform(delete("/deleteApplication")
                .param("applicationName", "abcdApplication"))
                .andExpect(status().isOk())
                .andExpect(content().string("abcdApplication"));
        System.out.println("Exit-com.lightningcd.api.DeploymentControllerTest.Test4DeleteApplication");
    }
}

