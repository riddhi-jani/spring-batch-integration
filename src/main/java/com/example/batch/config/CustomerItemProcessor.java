package com.example.batch.config;

import com.example.batch.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        String fullName = customer.getFirstName() + " " +customer.getLastName();
        customer.setFullName(fullName);
        return customer;
    }
}
