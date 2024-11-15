package it.polito.queuemanagementsystem.service;

import it.polito.queuemanagementsystem.dto.response.GetTicketResponseDTO;
import it.polito.queuemanagementsystem.dto.response.QueueStatusResponseDTO;
import it.polito.queuemanagementsystem.dto.response.ServiceResponseDTO;
import it.polito.queuemanagementsystem.model.Counter;
import it.polito.queuemanagementsystem.model.CounterServiceEntity;
import it.polito.queuemanagementsystem.model.CounterServiceId;
import it.polito.queuemanagementsystem.model.Service;
import it.polito.queuemanagementsystem.repository.CounterServiceRepository;
import it.polito.queuemanagementsystem.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CounterServiceRepository counterServiceRepository;

    @InjectMocks
    private ServiceService serviceService;

    private Service mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Mocking Service entity with service_id 5
        mockService = new Service();
        mockService.setServiceId(5L);
        mockService.setServiceAbbreviation("PPT");
        mockService.setLastTicketNumber(4);  // Last ticket issued was 4
        mockService.setQueueLength(4);  // 4 people already in the queue
        mockService.setAverageServiceTime(5); // Average service time is 5 minutes
    }

    @Test
    void generateTicket_ShouldReturnCorrectTicketCodeAndWaitingTime() {
        // Mocking the service to be found by serviceRepository
        when(serviceRepository.findById(5L)).thenReturn(Optional.of(mockService));

        // Mocking available counters for the service
        Counter counter1 = new Counter(1L);  // Counter 1 that can serve the service
        Counter counter2 = new Counter(2L);  // Counter 2 that can serve the service

        CounterServiceEntity counterServiceEntity1 = new CounterServiceEntity(new CounterServiceId(1L, 5L), counter1, mockService);
        CounterServiceEntity counterServiceEntity2 = new CounterServiceEntity(new CounterServiceId(2L, 5L), counter2, mockService);

        when(counterServiceRepository.findByServiceId(5L)).thenReturn(List.of(counterServiceEntity1, counterServiceEntity2));

        // Mocking that both counters can serve multiple services
        when(counterServiceRepository.countDistinctServicesForCounter(1L)).thenReturn(1);  // Counter 1 serves 1 service
        when(counterServiceRepository.countDistinctServicesForCounter(2L)).thenReturn(2);  // Counter 2 serves 2 services

        // Calling the actual service method
        GetTicketResponseDTO response = serviceService.generateTicket(5L);

        // Verifying the expected results
        assertEquals("PPT-5", response.ticketCode());
        assertEquals("00h15m49s", response.waitingTime());

        // Verify that the serviceRepository was saved once
        verify(serviceRepository, times(1)).save(any(Service.class));
    }

    @Test
    void generateTicket_ShouldThrowException_WhenServiceNotFound() {
        // Mocking service not found
        when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

        // Expecting an exception when the service is not found
        assertThrows(RuntimeException.class, () -> serviceService.generateTicket(5L));

        // Verifying that the serviceRepository.save() was never called
        verify(serviceRepository, times(0)).save(any(Service.class));
    }

    @Test
    void getQueueStatus_ShouldReturnQueueStatusDTOs() {
        // Mocking Service entities
        Service service1 = new Service(1L, "Package Delivering", "PKG", 15, 5, 0);
        Service service2 = new Service(2L, "Tax Payment", "TAX", 20, 3, 0);
        Service service3 = new Service(3L, "Passport", "PPT", 30, 4, 0);

        List<Service> mockServices = Arrays.asList(service1, service2, service3);

        // Mocking the serviceRepository call to return the list of services
        when(serviceRepository.findAll()).thenReturn(mockServices);

        // Calling the method under test
        List<QueueStatusResponseDTO> queueStatuses = serviceService.getQueuesStatus();

        // Verify that the repository method was called once
        verify(serviceRepository, times(1)).findAll();

        // Verify the result
        assertEquals(3, queueStatuses.size());
        assertEquals("Package Delivering", queueStatuses.get(0).serviceName());
        assertEquals(5, queueStatuses.get(0).queueLength());
        assertEquals("Tax Payment", queueStatuses.get(1).serviceName());
        assertEquals(3, queueStatuses.get(1).queueLength());
        assertEquals("Passport", queueStatuses.get(2).serviceName());
        assertEquals(4, queueStatuses.get(2).queueLength());
    }

    @Test
    void getQueueStatus_ShouldReturnEmptyList_WhenNoServices() {
        // Mocking the serviceRepository call to return an empty list
        when(serviceRepository.findAll()).thenReturn(List.of());

        // Calling the method under test
        List<QueueStatusResponseDTO> queueStatuses = serviceService.getQueuesStatus();

        // Verify that the repository method was called once
        verify(serviceRepository, times(1)).findAll();

        // Verify the result is an empty list
        assertEquals(0, queueStatuses.size());
    }

    @Test
    void getAllServices_ShouldReturnServiceDTOs() {
        // Mocking the repository to return Service entities
        Service service1 = new Service(1L, "Package Delivering", "PKG", 15, 0, 0);
        Service service2 = new Service(2L, "Tax Payments", "TAX", 20, 0, 0);
        Service service3 = new Service(3L, "Passport Services", "PPT", 30, 0, 0);

        List<Service> mockServices = Arrays.asList(service1, service2, service3);

        when(serviceRepository.findAll()).thenReturn(mockServices);

        // Calling the service method
        List<ServiceResponseDTO> serviceDTOs = serviceService.getAllServices();

        // Verifying that serviceRepository.findAll() was called once
        verify(serviceRepository, times(1)).findAll();

        // Asserting the returned values
        assertEquals(3, serviceDTOs.size());
        assertEquals("Package Delivering", serviceDTOs.get(0).serviceName());
        assertEquals("Tax Payments", serviceDTOs.get(1).serviceName());
        assertEquals("Passport Services", serviceDTOs.get(2).serviceName());
    }

    @Test
    void getAllServices_ShouldReturnEmptyList_WhenNoServices() {
        // Mock the repository to return an empty list
        when(serviceRepository.findAll()).thenReturn(List.of());

        // Calling the service method
        List<ServiceResponseDTO> serviceDTOs = serviceService.getAllServices();

        // Verifying that serviceRepository.findAll() was called once
        verify(serviceRepository, times(1)).findAll();

        // Asserting the result is an empty list
        assertEquals(0, serviceDTOs.size());
    }

}
