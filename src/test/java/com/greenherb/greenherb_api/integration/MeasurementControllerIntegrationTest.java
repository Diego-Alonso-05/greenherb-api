package com.greenherb.greenherb_api.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.repository.AlertRepository;
import com.greenherb.greenherb_api.repository.MeasurementRepository;
import com.greenherb.greenherb_api.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MeasurementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        alertRepository.deleteAll();
        measurementRepository.deleteAll();
        planRepository.deleteAll();

        Plan plan = Plan.builder()
                .type("regular")
                .minTemp(18)
                .maxTemp(28)
                .minHumidity(40)
                .maxHumidity(80)
                .active(true)
                .automaticIrrigation(true)
                .build();

        planRepository.save(plan);

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
    void createMeasurementInsideLimitsShouldNotCreateAlert() throws Exception {
        String body = """
                {
                  "temperature": 23,
                  "humidity": 60
                }
                """;

        mockMvc.perform(post("/measurements")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        assertThat(alertRepository.findAll()).isEmpty();
    }

    @Test
    void createMeasurementWithCriticalConditionsShouldCreateCriticalAlert() throws Exception {
        String body = """
                {
                  "temperature": 30,
                  "humidity": 30
                }
                """;

        mockMvc.perform(post("/measurements")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        assertThat(alertRepository.findAll()).hasSize(1);
        assertThat(alertRepository.findAll().get(0).getLevel()).isEqualTo("CRITICAL");
    }

    @Test
    void createMeasurementWithWarningConditionsShouldCreateWarningAlert() throws Exception {
        String body = """
                {
                  "temperature": 15,
                  "humidity": 60
                }
                """;

        mockMvc.perform(post("/measurements")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        assertThat(alertRepository.findAll()).hasSize(1);
        assertThat(alertRepository.findAll().get(0).getLevel()).isEqualTo("WARNING");
    }
}