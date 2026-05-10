package com.greenherb.greenherb_api.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.model.AlertStatus;
import com.greenherb.greenherb_api.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlertControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        alertRepository.deleteAll();
        token = loginAndGetToken();
    }

    private String loginAndGetToken() throws Exception {
        String body = """
                {
                  "username": "admin",
                  "password": "1234"
                }
                """;

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);

        if (json.has("token")) {
            return json.get("token").asText();
        }

        if (json.has("accessToken")) {
            return json.get("accessToken").asText();
        }

        return response.replace("\"", "");
    }

    @Test
    void getAlertsShouldReturnOk() throws Exception {
        mockMvc.perform(get("/alerts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void resolveAlertShouldChangeStatusToResolved() throws Exception {
        Alert alert = Alert.builder()
                .message("Test alert")
                .level("WARNING")
                .status(AlertStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Alert savedAlert = alertRepository.save(alert);

        mockMvc.perform(patch("/alerts/" + savedAlert.getId() + "/resolve")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RESOLVED"));
    }

    @Test
    void ignoreAlertWithJustificationShouldChangeStatusToIgnored() throws Exception {
        Alert alert = Alert.builder()
                .message("Test alert")
                .level("WARNING")
                .status(AlertStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Alert savedAlert = alertRepository.save(alert);

        String body = """
                {
                  "justification": "Ignored because it was a sensor error"
                }
                """;

        mockMvc.perform(patch("/alerts/" + savedAlert.getId() + "/ignore")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IGNORED"));
    }

    @Test
    void ignoreAlertWithoutJustificationShouldReturnBadRequest() throws Exception {
        Alert alert = Alert.builder()
                .message("Test alert")
                .level("WARNING")
                .status(AlertStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Alert savedAlert = alertRepository.save(alert);

        String body = """
                {
                  "justification": ""
                }
                """;

        mockMvc.perform(patch("/alerts/" + savedAlert.getId() + "/ignore")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}