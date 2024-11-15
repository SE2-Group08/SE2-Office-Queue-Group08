package it.polito.queuemanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CounterServiceEntity {

    @EmbeddedId
    private CounterServiceId id;

    @ManyToOne
    @MapsId("counterId")
    @JoinColumn(name = "counter_id")
    private Counter counter;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private Service service;

    public Long getServiceId() {
        return id.getServiceId();
    }
}