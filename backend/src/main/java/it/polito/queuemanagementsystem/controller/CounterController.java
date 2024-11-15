package it.polito.queuemanagementsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.polito.queuemanagementsystem.dto.response.NextCustomerResponseDTO;
import it.polito.queuemanagementsystem.service.CounterService;

@Tag(name = "Counters", description = "Counters API")
@RestController
@RequestMapping("/api/v1/counters")
public class CounterController {
    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/{id}/callnext")
    public ResponseEntity<NextCustomerResponseDTO> callNextCustomer(@PathVariable Long id) {
        System.out.println("Calling next customer for counter " + id);
        NextCustomerResponseDTO nextCustomerInfo = counterService.callNextCustomer(id);
        return ResponseEntity.ok(nextCustomerInfo);
    }
}
