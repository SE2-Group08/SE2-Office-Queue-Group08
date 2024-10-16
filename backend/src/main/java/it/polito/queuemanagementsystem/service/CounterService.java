package it.polito.queuemanagementsystem.service;

import java.util.ArrayList;
import java.util.List;

import it.polito.queuemanagementsystem.dto.response.NextCustomerResponseDTO;
import it.polito.queuemanagementsystem.model.CounterServiceEntity;
import it.polito.queuemanagementsystem.model.Service;
import it.polito.queuemanagementsystem.repository.CounterServiceRepository;
import it.polito.queuemanagementsystem.repository.ServiceRepository;
import jakarta.transaction.Transactional;

@org.springframework.stereotype.Service
public class CounterService {

    private final ServiceRepository serviceRepository;
    private final CounterServiceRepository counterServiceRepository;

    public CounterService(ServiceRepository serviceRepository, CounterServiceRepository counterServiceRepository) {
        this.serviceRepository = serviceRepository;
        this.counterServiceRepository = counterServiceRepository;
    }

    @Transactional
    public NextCustomerResponseDTO callNextCustomer(Long counterId) {
        // Find all services offered by the counter
        List<CounterServiceEntity> allServicesAvailable = counterServiceRepository.findByCounterId(counterId);

        // Find all the services info
        List<Service> availableServicesInfo = new ArrayList<>();
        for (CounterServiceEntity counterServiceEntity : allServicesAvailable) {
            availableServicesInfo.add(serviceRepository.findById(counterServiceEntity.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found")));
        }

        // Find the service with the longest queue, if two or more have the same length,
        // return the one with the lowest service time
        Service nextService = availableServicesInfo.stream()
                .max((s1, s2) -> {
                    int queueComparison = Integer.compare(s2.getQueueLength(), s1.getQueueLength());
                    if (queueComparison == 0) {
                        return Integer.compare(s1.getServiceTime(), s2.getServiceTime());
                    }
                    return queueComparison;
                })
                .orElseThrow(() -> new RuntimeException("No services available")); // this should never happen ig

        nextService.setQueueLength(nextService.getQueueLength() - 1);
        nextService.setLastTicketNumber(nextService.getLastTicketNumber() + 1);
        serviceRepository.save(nextService);

        return new NextCustomerResponseDTO(counterId, nextService.getServiceName(), nextService.getQueueLength());
    }
}