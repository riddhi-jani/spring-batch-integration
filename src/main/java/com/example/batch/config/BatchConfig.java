package com.example.batch.config;

import com.example.batch.entity.Customer;
import com.example.batch.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public Job job(JobRepository jobRepository, Step steps, JobCompletionNotificationImpl listener){
        return new JobBuilder("job",jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      ItemReader<Customer> reader,
                      ItemProcessor<Customer, Customer> processor,
                      ItemWriter<Customer> writer) {
        return new StepBuilder("steps", jobRepository)
                .<Customer, Customer> chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public FlatFileItemReader<Customer> reader(){

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customer")
                .resource(new ClassPathResource("customers.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id","firstName","lastName","company","city","country","contactNo","email","dob")
                .targetType(Customer.class)
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> itemProcessor(){
        return new CustomerItemProcessor();
    }
    @Bean
    public ItemWriter<Customer> itemWriter() {
        return new RepositoryItemWriterBuilder<Customer>()
                .repository(customerRepository)
                .methodName("save")
                .build();
    }

    @Autowired
    private CustomerRepository customerRepository;
}
