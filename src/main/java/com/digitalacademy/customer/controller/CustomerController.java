package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.CustomerModel;
import com.digitalacademy.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    //Member Variable //Setter Injection
//    @Autowired
//    CustomerService customerService;

    //Constructor Injection
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<CustomerModel> customerList (){
        return customerService.getCustomerList();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        CustomerModel customer = customerService.getCustomerById(id);
        if(customer != null){
            return ResponseEntity.ok(customer);
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = "firstName")
    public ResponseEntity<?> getCustomerByFirstName(@RequestParam(value = "firstName") String firstName) {
        List<CustomerModel> customer = customerService.getCustomerByFirstName(firstName);
        return customer != null && !customer.isEmpty() ? ResponseEntity.ok(customer)
                : ResponseEntity.notFound().build();

//        if(customer != null && customer.isEmpty()){
//            return ResponseEntity.ok(customer);
//        }
//        else{
//            return  ResponseEntity.notFound().build();
//        }
    }

//    @GetMapping("/name/{firstName}")
//    public ResponseEntity<?> getCustomerByFirstName(@PathVariable String firstName){
//        List<CustomerModel> customer = customerService.getCustomerByFirstName(firstName);
//        if(customer != null){
//            return ResponseEntity.ok(customer);
//        }
//        else{
//            return  ResponseEntity.notFound().build();
//        }
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postCustomer(@Valid @RequestBody CustomerModel body){
        CustomerModel customer = customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(@PathVariable Long id, @Valid @RequestBody CustomerModel body){
        body.setId(id);
        CustomerModel customer = customerService.updateCustomer(id, body);
        return customer != null ? ResponseEntity.ok(customer)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

}
