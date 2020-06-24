package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {

    public static List<CustomerModel> getCustomerList(){
        List<CustomerModel> customerList = new ArrayList<>();

        CustomerModel customer = new CustomerModel();
        customer.setId(1L);
        customer.setFirstName("Janice");
        customer.setLastName("Lewis");
        customer.setEmail("jnlewis@gmail.com");
        customer.setPhoneNo("0899876765");
        customer.setAge(38);
        customerList.add(customer);

        customer = new CustomerModel();
        customer.setId(2L);
        customer.setFirstName("Han");
        customer.setLastName("Jihun");
        customer.setEmail("hjhun@gmail.com");
        customer.setPhoneNo("0909788745");
        customer.setAge(21);
        customerList.add(customer);

        customer = new CustomerModel();
        customer.setId(3L);
        customer.setFirstName("Tiger");
        customer.setLastName("Soo");
        customer.setEmail("tigers@gmail.com");
        customer.setPhoneNo("0909755745");
        customer.setAge(23);
        customerList.add(customer);

        customer = new CustomerModel();
        customer.setId(4L);
        customer.setFirstName("Tiger");
        customer.setLastName("San");
        customer.setEmail("tigersn@gmail.com");
        customer.setPhoneNo("0909755744");
        customer.setAge(23);
        customerList.add(customer);

        return customerList;
    }

    public static CustomerModel getCustomer(){
        CustomerModel customer = new CustomerModel();
        customer.setId(1L);
        customer.setFirstName("Janice");
        customer.setLastName("Lewis");
        customer.setEmail("jnlewis@gmail.com");
        customer.setPhoneNo("0899876765");
        customer.setAge(38);
        return customer;
    }

    public static CustomerModel getReqCustomer(){
        CustomerModel customerReq = new CustomerModel();
        customerReq.setFirstName("Han");
        customerReq.setLastName("Jihun");
        customerReq.setEmail("hjhun@gmail.com");
        customerReq.setPhoneNo("0909788745");
        customerReq.setAge(21);
        return customerReq;
    }

    public static CustomerModel getResCustomer(){
        CustomerModel customerReturn = new CustomerModel();
        customerReturn.setId(1L);
        customerReturn.setFirstName("Han");
        customerReturn.setLastName("Jihun");
        customerReturn.setEmail("hjhun@gmail.com");
        customerReturn.setPhoneNo("0909788745");
        customerReturn.setAge(21);
        return customerReturn;
    }

    public static CustomerModel getReqNewCustomer(){
        CustomerModel reqCustomer = new CustomerModel();
        reqCustomer.setId(2L);
        reqCustomer.setFirstName("Han");
        reqCustomer.setLastName("Jihun");
        reqCustomer.setEmail("hjhun@gmail.com");
        reqCustomer.setPhoneNo("0909788745");
        reqCustomer.setAge(21);
        return reqCustomer;
    }

    public static CustomerModel getOldCustomer(){
        CustomerModel oldCustomer = new CustomerModel();
        oldCustomer.setId(2L);
        oldCustomer.setFirstName("Han");
        oldCustomer.setLastName("Jihun");
        oldCustomer.setEmail("hjhun@gmail.com");
        oldCustomer.setPhoneNo("0909788745");
        oldCustomer.setAge(20);
        return oldCustomer;
    }

}
