package com.javaprodigy.batch.config;

import com.javaprodigy.batch.entities.EmployeeEntity;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends JobExecutionListenerSupport {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    NotificationListener.class
  );

  private JdbcTemplate template;

  @Autowired
  public NotificationListener(final JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public void afterJob(final JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      LOGGER.info("Job finished! Verifying results");
      template
        .query(
          "SELECT * FROM employees",
          (rs, rowNum) ->
            new EmployeeEntity(
              UUID.fromString(rs.getString("id")),
              rs.getString("name"),
              rs.getBigDecimal("salary")
            )
        )
        .forEach(
          employee -> LOGGER.info("Found <" + employee + "> in the database")
        );
    }
  }
}
