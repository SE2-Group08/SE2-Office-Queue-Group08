package it.polito.queuemanagementsystem.service;


import it.polito.queuemanagementsystem.dto.response.GetTicketResponseDTO;
import it.polito.queuemanagementsystem.dto.response.QueueStatusResponseDTO;
import it.polito.queuemanagementsystem.dto.response.ServiceResponseDTO;
import it.polito.queuemanagementsystem.model.Counter;
import it.polito.queuemanagementsystem.model.CounterServiceEntity;
import it.polito.queuemanagementsystem.model.Service;
import it.polito.queuemanagementsystem.repository.CounterServiceRepository;
import it.polito.queuemanagementsystem.repository.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final CounterServiceRepository counterServiceRepository;

    public ServiceService(ServiceRepository serviceRepository, CounterServiceRepository counterServiceRepository) {
        this.serviceRepository = serviceRepository;
        this.counterServiceRepository = counterServiceRepository;
    }

    @Transactional
    public GetTicketResponseDTO generateTicket(Long serviceId) {
        // Find the service by ID
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // Increment the last ticket number (before updating the queue length)
        Integer newTicketNumber = service.getLastTicketNumber() + 1;

        // Get the CounterServiceEntity entities that represent counters serving this service
        List<CounterServiceEntity> availableCounters = counterServiceRepository.findByServiceId(serviceId);

        // Calculate waiting time based on the provided formula
        double waitingTimeInMinutes = calculateWaitingTime(service, availableCounters);

        // Format the waiting time into hours, minutes, and seconds
        String formattedWaitingTime = formatWaitingTime(waitingTimeInMinutes);

        // Now increment the queue length (after calculating waiting time)
        service.setQueueLength(service.getQueueLength() + 1);

        // Update the last ticket number and save the service
        service.setLastTicketNumber(newTicketNumber);
        serviceRepository.save(service);

        // Generate the ticket code (e.g., "PPT-1234")
        String ticketCode = service.getServiceAbbreviation() + "-" + newTicketNumber;

        // Return the ticket code and formatted waiting time
        return new GetTicketResponseDTO(ticketCode, formattedWaitingTime);
    }

    // Method to calculate waiting time using the given formula (returns time in minutes)
    private double calculateWaitingTime(Service service, List<CounterServiceEntity> availableCounters) {
        int nr = service.getQueueLength(); // Number of people in the queue for this service
        int tr = service.getAverageServiceTime(); // Service time for this request type

        double denominatorSum = 0;

        // Loop through all CounterServiceEntity entities that link counters with the requested service
        for (CounterServiceEntity counterServiceEntity : availableCounters) {
            Counter counter = counterServiceEntity.getCounter();
            int ki = counterServiceRepository.countDistinctServicesForCounter(counter.getCounterId()); // Number of services this counter can serve

            denominatorSum += 1.0 / ki; // If this counter can serve this request type
        }

        // Apply the waiting time formula
        return tr * (nr / denominatorSum + 0.5);
    }

    // Convert total waiting time in minutes to hours, minutes, and seconds in the format XXhXXmXXs
    private String formatWaitingTime(double waitingTimeInMinutes) {
        long totalSeconds = (long) (waitingTimeInMinutes * 60); // Convert minutes to total seconds

        long hours = totalSeconds / 3600;
        long remainingSecondsAfterHours = totalSeconds % 3600;
        long minutes = remainingSecondsAfterHours / 60;
        long seconds = remainingSecondsAfterHours % 60;

        // Return formatted time as "XXhXXmXXs"
        return String.format("%02dh%02dm%02ds", hours, minutes, seconds);
    }

    // Method to get the current queue lengths for all services
    public List<QueueStatusResponseDTO> getQueuesStatus() {
        // Retrieve all services from the repository and map them to DTOs
        return serviceRepository.findAll().stream()
                .map(service -> new QueueStatusResponseDTO(service.getServiceName(), service.getQueueLength()))
                .toList();
    }

    // Method to retrieve all services and map them to DTOs
    public List<ServiceResponseDTO> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        // Map the Service entities to ServiceDTOs and return them
        return services.stream()
                .map(service -> new ServiceResponseDTO(service.getServiceId(), service.getServiceName()))
                .toList();
    }
}
