package com.devtalk.batch.etl.config;

import com.devtalk.batch.etl.model.Users;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class BatchConfiguration {

    /**
     * Load the properties
     *//*
    @Value("${spring.datasource.driver}")
    private String databaseDriver;
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;*/

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;



    private static final String QUERY_FIND_USERS =
            "SELECT " +
                    "id, " +
                    "name, " +
                    "role, " +
                    "salary " +
                    "FROM Users";

    @Bean
    public ItemReader<Users> itemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Users>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY_FIND_USERS)
                .rowMapper(new UserRowMapper())
                .build();
    }

    @Bean
    public UserItemProcessor itemProcessor() {
        return new UserItemProcessor();
    }

    @Bean
    public UserItemWriter itemWriter() {
        /*FlatFileItemWriter<Users> itemWriter = new FlatFileItemWriter<>();
        itemWriter.setResource(new FileSystemResource("C:\\Users\\admin\\IdeaProjects\\spring-batch-db-etl-demo\\src\\main\\resources\\users.csv"));

        DelimitedLineAggregator<Users> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter("|");

        BeanWrapperFieldExtractor<Users> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "name", "role", "salary"});

        lineAggregator.setFieldExtractor(fieldExtractor);
        itemWriter.setLineAggregator(lineAggregator);
        return itemWriter;*/
        return new UserItemWriter();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .<Users, Users>chunk(10)
                .reader(itemReader(dataSource))
                .faultTolerant()
                .processor(itemProcessor())
                .writer(itemWriter()).build();
    }

    @Bean
    public Job userJob() {
        return jobBuilderFactory
                .get("user-etl-job")
                .incrementer(new RunIdIncrementer())
                .start(step1()).build();
    }

    @Bean
    public RestTemplate restTemplate() {
       return  new RestTemplate();
    }

}
