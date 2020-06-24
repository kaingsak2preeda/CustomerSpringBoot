package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.CustomerModel;
import com.digitalacademy.customer.repository.CustomerRepository;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list")
    @Test
    void testGetAllCustomer(){

        //Arrange
        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getCustomerList());

        //Act
        List<CustomerModel> resp = customerService.getCustomerList();

        //Assert
        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("Janice",resp.get(0).getFirstName());
        assertEquals("Lewis",resp.get(0).getLastName());
        assertEquals("jnlewis@gmail.com",resp.get(0).getEmail());
        assertEquals("0899876765",resp.get(0).getPhoneNo());
        assertEquals(38,resp.get(0).getAge().intValue());

        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("Han",resp.get(1).getFirstName());
        assertEquals("Jihun",resp.get(1).getLastName());
        assertEquals("hjhun@gmail.com",resp.get(1).getEmail());
        assertEquals("0909788745",resp.get(1).getPhoneNo());
        assertEquals(21,resp.get(1).getAge().intValue());

    }

    @DisplayName("Test get customer by ID should return customer")
    @Test
    void testGetCustomerById() {

        //Arrange
        Long reqParam = 1l;

        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getCustomer());

        //Act
        CustomerModel resp = customerService.getCustomerById(reqParam);

        //Assert
        assertEquals(1,resp.getId().intValue());
        assertEquals("Janice",resp.getFirstName());
        assertEquals("Lewis",resp.getLastName());
        assertEquals("jnlewis@gmail.com",resp.getEmail());
        assertEquals("0899876765",resp.getPhoneNo());
        assertEquals(38,resp.getAge().intValue());
    }

    @DisplayName("Test get customer by name should return customer")
    @Test
    void testGetCustomerByName() {

        //Arrange
        String name = "Tiger";

        when(customerRepository.findByFirstName(name)).thenReturn(CustomerSupportTest.getCustomerList());

        //Act
        List<CustomerModel> resp = customerService.getCustomerByFirstName(name);

        //Assert
        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("Janice",resp.get(0).getFirstName());
        assertEquals("Lewis",resp.get(0).getLastName());
        assertEquals("jnlewis@gmail.com",resp.get(0).getEmail());
        assertEquals("0899876765",resp.get(0).getPhoneNo());
        assertEquals(38,resp.get(0).getAge().intValue());

        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("Han",resp.get(1).getFirstName());
        assertEquals("Jihun",resp.get(1).getLastName());
        assertEquals("hjhun@gmail.com",resp.get(1).getEmail());
        assertEquals("0909788745",resp.get(1).getPhoneNo());
        assertEquals(21,resp.get(1).getAge().intValue());

        assertEquals(3,resp.get(2).getId().intValue());
        assertEquals("Tiger",resp.get(2).getFirstName());
        assertEquals("Soo",resp.get(2).getLastName());
        assertEquals("tigers@gmail.com",resp.get(2).getEmail());
        assertEquals("0909755745",resp.get(2).getPhoneNo());
        assertEquals(23,resp.get(2).getAge().intValue());

        assertEquals(4,resp.get(3).getId().intValue());
        assertEquals("Tiger",resp.get(3).getFirstName());
        assertEquals("San",resp.get(3).getLastName());
        assertEquals("tigersn@gmail.com",resp.get(3).getEmail());
        assertEquals("0909755744",resp.get(3).getPhoneNo());
        assertEquals(23,resp.get(3).getAge().intValue());
    }

    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer() {
        //Arrange
        when(customerRepository.save(CustomerSupportTest.getReqCustomer())).thenReturn(CustomerSupportTest.getResCustomer());

        //Act
        CustomerModel resp = customerService.createCustomer(CustomerSupportTest.getReqCustomer());

        //Assert
        assertEquals(1,resp.getId().intValue());
        assertEquals("Han",resp.getFirstName());
        assertEquals("Jihun",resp.getLastName());
        assertEquals("hjhun@gmail.com",resp.getEmail());
        assertEquals("0909788745",resp.getPhoneNo());
        assertEquals(21,resp.getAge().intValue());
    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() {
        //Arrange
        Long reqId = 2L;

        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());
        when(customerRepository.save(CustomerSupportTest.getReqNewCustomer()))
                .thenReturn(CustomerSupportTest.getReqNewCustomer());

        //Act
        CustomerModel resp = customerService.updateCustomer(reqId, CustomerSupportTest.getReqNewCustomer());

        //Assert
        assertEquals(2,resp.getId().intValue());
        assertEquals("Han",resp.getFirstName());
        assertEquals("Jihun",resp.getLastName());
        assertEquals("hjhun@gmail.com",resp.getEmail());
        assertEquals("0909788745",resp.getPhoneNo());
        assertEquals(21,resp.getAge().intValue());
    }

    @DisplayName("Test update customer should return fail")
    @Test
    void testUpdateCustomerFail() {
        //Arrange
        Long reqId = 4L;

        when(customerRepository.findAllById(reqId)).thenReturn(null);
        when(customerRepository.save(CustomerSupportTest.getReqNewCustomer())).thenReturn(CustomerSupportTest.getReqNewCustomer());

        //Act
        CustomerModel resp = customerService.updateCustomer(reqId, CustomerSupportTest.getReqNewCustomer());

        //Assert
        assertEquals(null,resp);
    }

    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer() {
        //Arrange
        Long reqId = 2L;

        doNothing().when(customerRepository).deleteById(reqId);

        //Act
        boolean resp = customerService.deleteCustomer(reqId);

        //Assert
        assertEquals(true,resp);
        assertTrue(resp);
        assertTrue(customerService.deleteCustomer(reqId));

    }

    @DisplayName("Test delete customer should return false")
    @Test
    void testDeleteCustomerFail() {
        //Arrange
        Long reqId = 2L;

        doThrow(EmptyResultDataAccessException.class).when(customerRepository).deleteById(reqId);

        //Act
        boolean resp = customerService.deleteCustomer(reqId);

        //Assert
        assertEquals(false,resp);
        assertFalse(resp);
        assertFalse(customerService.deleteCustomer(reqId));
    }

}
