package it.polito.queuemanagementsystem.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.polito.queuemanagementsystem.dto.request.GetTicketRequestDTO;
import it.polito.queuemanagementsystem.dto.response.GetTicketResponseDTO;
import it.polito.queuemanagementsystem.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Tickets", description = "Tickets API")
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final ServiceService serviceService;

    public TicketController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping("/generate")
    public ResponseEntity<GetTicketResponseDTO> generateTicket(@RequestBody GetTicketRequestDTO serviceIdRequest) {
        // Call the service to generate the ticket code and calculate the waiting time
        GetTicketResponseDTO ticketResponse = serviceService.generateTicket(serviceIdRequest.serviceId());
        return ResponseEntity.ok(ticketResponse);
    }
}