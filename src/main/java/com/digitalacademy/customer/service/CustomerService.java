package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.CustomerModel;
import com.digitalacademy.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerModel> getCustomerList () {
        return customerRepository.findAll();
    }

    public CustomerModel getCustomerById (Long id) {
        return customerRepository.findAllById(id);
    }

    public List<CustomerModel> getCustomerByFirstName (String firstName) {
        return customerRepository.findByFirstName(firstName);
    }

    public CustomerModel createCustomer(CustomerModel customer){
        return customerRepository.save(customer);
    }

    public CustomerModel updateCustomer(Long id, CustomerModel customerReq){
        return customerRepository.findAllById(id) != null ?
                customerRepository.save(customerReq) : null;
    }

    public boolean deleteCustomer(Long id){
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }
}
