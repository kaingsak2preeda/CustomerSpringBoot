package com.digitalacademy.customer.repository;

import com.digitalacademy.customer.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    List<CustomerModel> findByFirstName(String firstName);
    CustomerModel findAllById(Long id);
}
