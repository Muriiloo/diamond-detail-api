package com.diamonddetail.api.repository;

import com.diamonddetail.api.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {
}
