package it.polito.queuemanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.queuemanagementsystem.dto.request.GetTicketRequestDTO;
import it.polito.queuemanagementsystem.dto.response.GetTicketResponseDTO;
import it.polito.queuemanagementsystem.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generateTicket_ShouldReturnTicketCodeAndWaitingTime() throws Exception {
        // Mock the response we expect from ServiceService
        GetTicketResponseDTO mockResponse = new GetTicketResponseDTO("PPT-5", "00h15m49s");

        // Mocking the serviceService call to return the mocked response
        Mockito.when(serviceService.generateTicket(any(Long.class)))
                .thenReturn(mockResponse);

        // Create a request DTO with the serviceId = 5
        GetTicketRequestDTO requestDTO = new GetTicketRequestDTO(5L);

        // Perform the POST request and check the result
        mockMvc.perform(post("/api/v1/tickets/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))) // Serialize requestDTO to JSON
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.ticketCode").value("PPT-5")) // Check the ticketCode
                .andExpect(jsonPath("$.waitingTime").value("00h15m49s")); // Check the waitingTime
    }

    @Test
    void generateTicket_ShouldReturnCustomErrorResponse_WhenServiceNotFound() throws Exception {
        // Mocking the serviceService to throw an exception when the service is not found
        Mockito.when(serviceService.generateTicket(any(Long.class)))
                .thenThrow(new RuntimeException("Service not found"));

        // Create a request DTO with the serviceId = 99
        GetTicketRequestDTO requestDTO = new GetTicketRequestDTO(99L);

        // Perform the POST request and check the result
        mockMvc.perform(post("/api/v1/tickets/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))) // Serialize requestDTO to JSON
                .andExpect(status().isInternalServerError()) // Expect HTTP 500 Internal Server Error
                .andExpect(jsonPath("$.statusCode").value(500)) // Check the status code
                .andExpect(jsonPath("$.message").value("Service not found")) // Check the message
                .andExpect(jsonPath("$.timestamp").exists()); // Check if timestamp exists
    }
}
