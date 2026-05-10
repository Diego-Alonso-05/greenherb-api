package com.greenherb.greenherb_api.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenherb.greenherb_api.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        planRepository.deleteAll();
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
    void createValidPlanShouldReturnOk() throws Exception {
        String body = """
                {
                  "type": "regular",
                  "minTemp": 18,
                  "maxTemp": 28,
                  "minHumidity": 40,
                  "maxHumidity": 80,
                  "active": true,
                  "automaticIrrigation": true
                }
                """;

        mockMvc.perform(post("/plans")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("regular"));
    }

    @Test
    void createPlanWithInvalidTemperatureRangeShouldReturnBadRequest() throws Exception {
        String body = """
                {
                  "type": "regular",
                  "minTemp": 30,
                  "maxTemp": 20,
                  "minHumidity": 40,
                  "maxHumidity": 80,
                  "active": true,
                  "automaticIrrigation": true
                }
                """;

        mockMvc.perform(post("/plans")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPlansShouldReturnOk() throws Exception {
        mockMvc.perform(get("/plans")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}