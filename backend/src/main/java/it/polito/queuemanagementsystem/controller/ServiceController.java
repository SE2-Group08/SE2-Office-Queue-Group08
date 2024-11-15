package it.polito.queuemanagementsystem.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.polito.queuemanagementsystem.dto.response.QueueStatusResponseDTO;
import it.polito.queuemanagementsystem.dto.response.ServiceResponseDTO;
import it.polito.queuemanagementsystem.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Services", description = "Services API")
@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // Endpoint to get the current queue lengths for all services
    @GetMapping("/queues/status")
    public ResponseEntity<List<QueueStatusResponseDTO>> getQueuesStatus() {
        System.out.println("Getting queues status");
        List<QueueStatusResponseDTO> queuesStatus = serviceService.getQueuesStatus();
        return ResponseEntity.ok(queuesStatus); // Return 200 OK with the list of queue statuses
    }

    // Endpoint to get the list of all services
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices() {
        // Call the service layer to get all services
        List<ServiceResponseDTO> services = serviceService.getAllServices();
        return ResponseEntity.ok(services); // Return 200 OK with the list of services
    }
}
