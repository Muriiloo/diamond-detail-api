package com.diamonddetail.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="services")
public class ServiceEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private BigDecimal price;

    @ManyToMany(mappedBy = "services")
    private List<AppointmentEntity> appointments;
}
