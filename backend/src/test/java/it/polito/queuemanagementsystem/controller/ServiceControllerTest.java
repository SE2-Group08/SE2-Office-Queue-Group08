package it.polito.queuemanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.queuemanagementsystem.dto.response.QueueStatusResponseDTO;
import it.polito.queuemanagementsystem.dto.response.ServiceResponseDTO;
import it.polito.queuemanagementsystem.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
class ServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getQueueStatus_ShouldReturnQueueStatusList() throws Exception {
        // Mock the response we expect from serviceService
        List<QueueStatusResponseDTO> mockQueueStatuses = Arrays.asList(
                new QueueStatusResponseDTO("Package Delivering", 5),
                new QueueStatusResponseDTO("Tax Payment", 3),
                new QueueStatusResponseDTO("Passport", 4)
        );

        // Mocking the serviceService call to return the mocked response
        Mockito.when(serviceService.getQueuesStatus()).thenReturn(mockQueueStatuses);

        // Perform the GET request and check the result
        mockMvc.perform(get("/api/v1/services/queues/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$[0].serviceName").value("Package Delivering"))
                .andExpect(jsonPath("$[0].queueLength").value(5))
                .andExpect(jsonPath("$[1].serviceName").value("Tax Payment"))
                .andExpect(jsonPath("$[1].queueLength").value(3))
                .andExpect(jsonPath("$[2].serviceName").value("Passport"))
                .andExpect(jsonPath("$[2].queueLength").value(4));
    }

    @Test
    void getQueueStatus_ShouldReturnEmptyList_WhenNoServices() throws Exception {
        // Mock the serviceService call to return an empty list
        Mockito.when(serviceService.getQueuesStatus()).thenReturn(List.of());

        // Perform the GET request and check the result
        mockMvc.perform(get("/api/v1/services/queues/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().json("[]")); // Expect empty list
    }

    @Test
    void getAllServices_ShouldReturnServiceList() throws Exception {
        // Mocking the response from ServiceService
        List<ServiceResponseDTO> mockServices = Arrays.asList(
                new ServiceResponseDTO(1L, "Package Delivering"),
                new ServiceResponseDTO(2L, "Tax Payments"),
                new ServiceResponseDTO(3L, "Passport Services")
        );

        // When the service is called, return the mocked list
        Mockito.when(serviceService.getAllServices()).thenReturn(mockServices);

        // Perform the GET request and check the result
        mockMvc.perform(get("/api/v1/services")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(jsonPath("$[0].serviceId").value(1))
                .andExpect(jsonPath("$[0].serviceName").value("Package Delivering"))
                .andExpect(jsonPath("$[1].serviceId").value(2))
                .andExpect(jsonPath("$[1].serviceName").value("Tax Payments"))
                .andExpect(jsonPath("$[2].serviceId").value(3))
                .andExpect(jsonPath("$[2].serviceName").value("Passport Services"));
    }

    @Test
    void getAllServices_ShouldReturnEmptyList_WhenNoServices() throws Exception {
        // Mock the serviceService call to return an empty list
        Mockito.when(serviceService.getAllServices()).thenReturn(List.of());

        // Perform the GET request and check the result
        mockMvc.perform(get("/api/v1/services")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().json("[]")); // Expect an empty JSON array
    }


}
