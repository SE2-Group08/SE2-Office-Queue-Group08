package it.polito.queuemanagementsystem.service;

import java.util.ArrayList;
import java.util.List;

import it.polito.queuemanagementsystem.dto.response.NextCustomerResponseDTO;
import it.polito.queuemanagementsystem.exception.CounterNotFoundException;
import it.polito.queuemanagementsystem.model.CounterServiceEntity;
import it.polito.queuemanagementsystem.model.Service;
import it.polito.queuemanagementsystem.repository.CounterServiceRepository;
import it.polito.queuemanagementsystem.repository.ServiceRepository;
import jakarta.transaction.Transactional;

@org.springframework.stereotype.Service // TODO: rename the service model to ServiceEntity because I cant import this

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
        if (allServicesAvailable.isEmpty()) {
            throw new CounterNotFoundException("Counter " + counterId + " does not exist");
        }

        // Find all the services info
        List<Service> availableServicesInfo = new ArrayList<>();
        for (CounterServiceEntity counterServiceEntity : allServicesAvailable) {
            availableServicesInfo.add(serviceRepository.findById(counterServiceEntity.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found")));
        }

        // find if all queues are empty
        if (availableServicesInfo.stream().allMatch(service -> service.getQueueLength() == 0)) {
            throw new RuntimeException("All queues are empty");
        } else {
            // Otherwise find the service with the longest queue, if two or more have the
            // same length,
            // return the one with the lowest service time
            Service nextService = availableServicesInfo.stream()
                    .max((s1, s2) -> {
                        int queueComparison = Integer.compare(s1.getQueueLength(), s2.getQueueLength());
                        if (queueComparison == 0) {
                            return Integer.compare(s1.getAverageServiceTime(), s2.getAverageServiceTime());
                        }
                        return queueComparison;
                    })
                    .orElseThrow(() -> new RuntimeException("No services available")); // this should never happen ig

            // Update the queue length and ticket number
            nextService.setQueueLength(nextService.getQueueLength() - 1);
            nextService.setLastTicketNumber(nextService.getLastTicketNumber() + 1);
            serviceRepository.save(nextService);
            return new NextCustomerResponseDTO(counterId, nextService.getServiceName(),
                    nextService.getServiceAbbreviation() + "-" + (nextService.getLastTicketNumber() - 1));
        }
    }
}