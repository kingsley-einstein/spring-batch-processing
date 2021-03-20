package com.javaprodigy.batch.config;

import com.javaprodigy.batch.entities.EmployeeEntity;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeEntityProcessor
  implements ItemProcessor<EmployeeEntity, EmployeeEntity> {

  @Override
  public EmployeeEntity process(final EmployeeEntity employeeEntity) {
    final EmployeeEntity processedEmployeeEntity = new EmployeeEntity(
      employeeEntity.getId(),
      employeeEntity.getName(),
      employeeEntity.getSalary()
    );
    return processedEmployeeEntity;
  }
}
