package com.javaprodigy.batch.data;

import com.javaprodigy.batch.entities.EmployeeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
  extends JpaRepository<EmployeeEntity, UUID> {}
