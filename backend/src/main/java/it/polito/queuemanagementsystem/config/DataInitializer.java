package it.polito.queuemanagementsystem.config;

import it.polito.queuemanagementsystem.model.Counter;
import it.polito.queuemanagementsystem.model.CounterServiceEntity;
import it.polito.queuemanagementsystem.model.CounterServiceId;
import it.polito.queuemanagementsystem.model.Service;
import it.polito.queuemanagementsystem.repository.CounterRepository;
import it.polito.queuemanagementsystem.repository.CounterServiceRepository;
import it.polito.queuemanagementsystem.repository.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ServiceRepository serviceRepository;
    private final CounterRepository counterRepository;
    private final CounterServiceRepository counterServiceRepository;

    public DataInitializer(ServiceRepository serviceRepository,
                           CounterRepository counterRepository,
                           CounterServiceRepository counterServiceRepository) {
        this.serviceRepository = serviceRepository;
        this.counterRepository = counterRepository;
        this.counterServiceRepository = counterServiceRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Insert data into Service table
        Service service1 = new Service(null, "Package Delivery", "PKG", 15, 0, 0);
        Service service2 = new Service(null, "Tax Payments", "TAX", 20, 0, 0);
        Service service3 = new Service(null, "Public Administration Payments", "PAP", 15, 0, 0);
        Service service4 = new Service(null, "Banking Services", "BNK", 30, 0, 0);
        Service service5 = new Service(null, "Passport Issuance", "PPT", 5, 0, 0);
        Service service6 = new Service(null, "Telecommunication Services", "TEL", 10, 0, 0);

        serviceRepository.saveAll(List.of(service1, service2, service3, service4, service5, service6));

        // Insert data into Counter table
        Counter counter1 = new Counter(null);
        counter1.setCounterId(1L);
        Counter counter2 = new Counter(null);
        counter2.setCounterId(2L);
        Counter counter3 = new Counter(null);
        counter3.setCounterId(3L);

        counterRepository.saveAll(List.of(counter1, counter2, counter3));

        // Insert data into CounterServiceEntity table
        CounterServiceEntity cs1 = new CounterServiceEntity(new CounterServiceId(counter1.getCounterId(), service1.getServiceId()), counter1, service1);
        CounterServiceEntity cs2 = new CounterServiceEntity(new CounterServiceId(counter2.getCounterId(), service2.getServiceId()), counter2, service2);
        CounterServiceEntity cs3 = new CounterServiceEntity(new CounterServiceId(counter3.getCounterId(), service5.getServiceId()), counter3, service5);
        CounterServiceEntity cs4 = new CounterServiceEntity(new CounterServiceId(counter1.getCounterId(), service4.getServiceId()), counter1, service4);
        CounterServiceEntity cs5 = new CounterServiceEntity(new CounterServiceId(counter2.getCounterId(), service5.getServiceId()), counter2, service5);
        CounterServiceEntity cs6 = new CounterServiceEntity(new CounterServiceId(counter1.getCounterId(), service6.getServiceId()), counter1, service6);

        counterServiceRepository.saveAll(List.of(cs1, cs2, cs3, cs4, cs5, cs6));
    }
}
