package com.example.registrationsystem.controller;

import com.example.registrationsystem.ExternalSystemConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = ExternalSystemConfig.PostgresInitializer.class)
@ActiveProfiles("test")
class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/api/v1/operator";

    @Test
    void findAllRequests_shouldReturnRequestDtoWithPagination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("order", "DESC")
                        .param("page", "0"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findAllRequestsByUserName_shouldReturnRequestDtoWithPagination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/by-username")
                        .param("userName", "ad")
                        .param("order", "DESC")
                        .param("page", "0"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void acceptRequest_shouldChangeStatusToAccepted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(URL + "/accept")
                        .param("requestId", "3"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void rejectRequest_shouldChangeStatusToRejected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(URL + "/reject")
                        .param("requestId", "7"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}