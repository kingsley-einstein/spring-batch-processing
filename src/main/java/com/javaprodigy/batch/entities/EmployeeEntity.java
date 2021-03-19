package com.javaprodigy.batch.entities;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class EmployeeEntity implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public UUID id;

  @NotNull(message = "Name cannot be null")
  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotNull(message = "Salary is required")
  private BigDecimal salary;

  public EmployeeEntity() {}

  public EmployeeEntity(
    final UUID id,
    final String name,
    final BigDecimal salary
  ) {
    this.id = id;
    this.name = name;
    this.salary = salary;
  }

  public UUID getId() {
    return id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setSalary(final BigDecimal salary) {
    this.salary = salary;
  }

  public BigDecimal getSalary() {
    return salary;
  }
}
