package com.javaprodigy.batch.config;

import com.javaprodigy.batch.entities.EmployeeEntity;
import java.util.UUID;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEntityFieldSetMapper
  implements FieldSetMapper<EmployeeEntity> {

  @Override
  public EmployeeEntity mapFieldSet(FieldSet fieldSet) {
    final EmployeeEntity e = new EmployeeEntity(
      UUID.fromString(fieldSet.readString("id")),
      fieldSet.readString("name"),
      fieldSet.readBigDecimal("salary")
    );
    return e;
  }
}
