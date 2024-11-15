package it.polito.queuemanagementsystem.repository;

import it.polito.queuemanagementsystem.model.CounterServiceEntity;
import it.polito.queuemanagementsystem.model.CounterServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterServiceRepository extends JpaRepository<CounterServiceEntity, CounterServiceId> {

    @Query("SELECT cs FROM CounterServiceEntity cs WHERE cs.service.serviceId = :serviceId")
    List<CounterServiceEntity> findByServiceId(Long serviceId);

    @Query("SELECT COUNT(DISTINCT cs.service) FROM CounterServiceEntity cs WHERE cs.counter.counterId = :counterId")
    int countDistinctServicesForCounter(Long counterId);

    @Query("SELECT cs FROM CounterServiceEntity cs WHERE cs.counter.counterId = :counterId")
    List<CounterServiceEntity> findByCounterId(Long counterId);
}
