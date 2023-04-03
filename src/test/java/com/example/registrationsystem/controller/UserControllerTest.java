package com.example.registrationsystem.controller;

import com.example.registrationsystem.ExternalSystemConfig;
import com.example.registrationsystem.dto.EditRequestDto;
import com.example.registrationsystem.dto.RequestDto;
import com.example.registrationsystem.util.JsonUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/api/v1/user";
    RequestDto requestDto;
    EditRequestDto editRequestDto;

    @BeforeAll
    public void setup() {
        requestDto = RequestDto.builder()
                .requestText("Some Text")
                .userId(1)
                .build();
        editRequestDto = EditRequestDto.builder()
                .id(2)
                .requestText("Some Updated Text")
                .build();
    }

    @Test
    void createNewRequest_shouldCreateNewRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/new")
                        .content(JsonUtil.writeValue(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void editDraftRequest_shouldChangeRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(URL + "/edit")
                        .content(JsonUtil.writeValue(editRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void sendForReview_shouldSendRequestForReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(URL + "/send")
                        .param("requestId", "2"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findRequestsByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/all-requests/3")
                        .param("order", "DESC")
                        .param("page", "0"))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(status().isOk())
                .andDo(print());
    }
}