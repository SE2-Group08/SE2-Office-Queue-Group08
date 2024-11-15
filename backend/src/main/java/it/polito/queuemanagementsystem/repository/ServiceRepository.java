package it.polito.queuemanagementsystem.repository;

import it.polito.queuemanagementsystem.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
