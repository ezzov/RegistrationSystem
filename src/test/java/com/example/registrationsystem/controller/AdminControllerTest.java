package com.example.registrationsystem.controller;

import com.example.registrationsystem.ExternalSystemConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/api/v1/admin";

    @Test
    @WithMockUser(username = "Valery", authorities = "ADMIN", password = "Valery")
    void findAllUsers_shouldReturnListOfUserDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/all-users"))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Valery", authorities = "ADMIN", password = "Valery")
    void findUserByUserName_shouldReturnUserDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/user")
                        .param("userName", "Adam"))
                .andExpect(jsonPath("$.email").value("adam@gmail.com"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Valery", authorities = "ADMIN", password = "Valery")
    void setOperatorRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(URL + "/set-role")
                .param("userId", "3"))
                .andExpect(jsonPath("$.email").value("adam@gmail.com"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}