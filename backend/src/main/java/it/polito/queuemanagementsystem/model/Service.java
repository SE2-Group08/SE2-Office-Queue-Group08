package it.polito.queuemanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVICE")
public class Service {

    // This class include information about service and its own queue
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false, unique = true)
    private String serviceAbbreviation;

    private Integer averageServiceTime;
    private Integer queueLength = 0; // Default to 0 for a new service
    private Integer lastTicketNumber = 0; // Default to 0 for a new service
}