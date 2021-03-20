package com.javaprodigy.batch.config;

import com.javaprodigy.batch.entities.EmployeeEntity;
import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  JobBuilderFactory jobBuilderFactory;

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  @Bean
  public FlatFileItemReader<EmployeeEntity> reader() {
    return new FlatFileItemReaderBuilder<EmployeeEntity>()
      .name("employeeItemReader")
      .resource(new ClassPathResource("Employees.csv"))
      .delimited()
      .names(new String[] { "id", "name", "salary" })
      .lineMapper(lineMapper())
      .fieldSetMapper(
        new BeanWrapperFieldSetMapper<EmployeeEntity>() {
          {
            setTargetType(EmployeeEntity.class);
          }
        }
      )
      .build();
  }

  @Bean
  public LineMapper<EmployeeEntity> lineMapper() {
    final DefaultLineMapper<EmployeeEntity> defaultLineMapper = new DefaultLineMapper<>();
    final DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    final EmployeeEntityFieldSetMapper employeeEntityFieldSetMapper = new EmployeeEntityFieldSetMapper();

    delimitedLineTokenizer.setDelimiter(";");
    delimitedLineTokenizer.setStrict(false);
    delimitedLineTokenizer.setNames(new String[] { "id", "name", "salary" });

    defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
    defaultLineMapper.setFieldSetMapper(employeeEntityFieldSetMapper);

    return defaultLineMapper;
  }

  @Bean
  public EmployeeEntityProcessor processor() {
    return new EmployeeEntityProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<EmployeeEntity> itemWriter(
    final DataSource dataSource
  ) {
    return new JdbcBatchItemWriterBuilder<EmployeeEntity>()
      .itemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<>()
      )
      .sql(
        "INSERT INTO employees (id, name, salary) VALUES (:id, :name, :salary)"
      )
      .dataSource(dataSource)
      .build();
  }
}
