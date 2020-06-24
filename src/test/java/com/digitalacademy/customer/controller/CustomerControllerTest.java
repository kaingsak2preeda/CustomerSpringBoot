package com.digitalacademy.customer.controller;


import com.digitalacademy.customer.model.CustomerModel;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;
    public static String urlCustomerList = "/customer/list/";
    public static String urlCustomer = "/customer";

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception{
        //Arrange
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getCustomerList());
        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //Act
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        //Assert
        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Janice",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("Lewis",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("jnlewis@gmail.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals("0899876765",jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals(38,jsonArray.getJSONObject(0).get("age"));

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("Han",jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("Jihun",jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("hjhun@gmail.com",jsonArray.getJSONObject(1).get("email"));
        assertEquals("0909788745",jsonArray.getJSONObject(1).get("phoneNo"));
        assertEquals(21,jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById() throws Exception{
        //Arrange
        Long reqId = 1L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getCustomer());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //Act
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        //Assert
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("Janice",jsonObject.get("firstName"));
        assertEquals("Lewis",jsonObject.get("lastName"));
        assertEquals("jnlewis@gmail.com",jsonObject.get("email"));
        assertEquals("0899876765",jsonObject.get("phoneNo"));
        assertEquals(38,jsonObject.get("age"));
    }

    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        //Arrange
        Long reqId = 6L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "/" + reqId))
                .andExpect(status().isNotFound()) //Act+Assert at isNotFound
                .andReturn();
    }

    @DisplayName("Test get customer by name should return customer")
    @Test
    void testGetCustomerByName() throws Exception{
        //Arrange
        String reqName = "Tiger";
        when(customerService.getCustomerByFirstName(reqName)).thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?firstName=" + reqName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        //Act
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());

        //Assert
        assertEquals("3",jsonArray.getJSONObject(2).get("id").toString());
        assertEquals("Tiger",jsonArray.getJSONObject(2).get("firstName"));
        assertEquals("Soo",jsonArray.getJSONObject(2).get("lastName"));
        assertEquals("tigers@gmail.com",jsonArray.getJSONObject(2).get("email"));
        assertEquals("0909755745",jsonArray.getJSONObject(2).get("phoneNo"));
        assertEquals(23,jsonArray.getJSONObject(2).get("age"));
    }

    @DisplayName("Test get customer by name should return not found")
    @Test
    void testGetCustomerByNameNotFound() throws Exception {
        //Arrange
        String reqName = "";

        when(customerService.getCustomerByFirstName(reqName)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?firstName=" + reqName))
                .andExpect(status().isNotFound()) //Act+Assert at isNotFound
                .andReturn();
    }

    @DisplayName("Test create customer should return customer")
    @Test
    void testCreateCustomer() throws Exception {
        //Arrange
        CustomerModel reqcustomer = CustomerSupportTest.getReqCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String reqJSON = objectWriter.writeValueAsString(reqcustomer);

        when(customerService.createCustomer(reqcustomer)).thenReturn(CustomerSupportTest.getResCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer+ "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJSON))
                .andExpect(status().isCreated())
                .andReturn();

        //Act
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        //Assert
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("Han",jsonObject.get("firstName"));
        assertEquals("Jihun",jsonObject.get("lastName"));
        assertEquals("hjhun@gmail.com",jsonObject.get("email"));
        assertEquals("0909788745",jsonObject.get("phoneNo"));
        assertEquals(21,jsonObject.get("age"));
    }

    @DisplayName("Test create customer with firstname is empty")
    @Test
    void testCreateCustomerWithNameIsEmpty() throws Exception {
        //Arrange
        CustomerModel reqcustomer = CustomerSupportTest.getReqCustomer();
        reqcustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String reqJSON = objectWriter.writeValueAsString(reqcustomer);

        when(customerService.createCustomer(reqcustomer)).thenReturn(CustomerSupportTest.getResCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJSON))
                .andExpect(status().isBadRequest()) //Act
                .andReturn();

        //Assert
        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalacademy.customer.controller.CustomerController.postCustomer(com.digitalacademy.customer.model.CustomerModel): [Field error in object 'customerModel' on field 'firstName': rejected value []; codes [Size.customerModel.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customerModel.firstName,firstName]; arguments []; default message [firstName],255,1]; default message [Pls types first name btw 1-255]] ",
                mvcResult.getResolvedException().getMessage());

    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception {
        //Arrange
        Long reqId = 2L;
        CustomerModel reqcustomer = CustomerSupportTest.getOldCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String reqJSON = objectWriter.writeValueAsString(reqcustomer);

        when(customerService.updateCustomer(reqId,reqcustomer)).thenReturn(CustomerSupportTest.getReqNewCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer+ "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJSON))
                .andExpect(status().isOk())
                .andReturn();

        //Act
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        //Assert
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("Han",jsonObject.get("firstName"));
        assertEquals("Jihun",jsonObject.get("lastName"));
        assertEquals("hjhun@gmail.com",jsonObject.get("email"));
        assertEquals("0909788745",jsonObject.get("phoneNo"));
        assertEquals(21,jsonObject.get("age"));
    }

    @DisplayName("Test update customer should return id not found")
    @Test
    void testUpdateCustomerNotFound() throws Exception {
        //Arrange
        Long reqId = 2L;
        CustomerModel reqcustomer = CustomerSupportTest.getOldCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String reqJSON = objectWriter.writeValueAsString(reqcustomer);

        when(customerService.updateCustomer(reqId,reqcustomer)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer+ "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJSON))
                .andExpect(status().isNotFound()) //Act+Assert at isNotFound
                .andReturn();

        verify(customerService, times(1)).updateCustomer(reqId,reqcustomer);
    }

    @DisplayName("Test delete customer should return success")
    @Test
    void testDeleteCustomer() throws Exception {
        //Arrange
        Long reqId = 4L;
        when(customerService.deleteCustomer(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer+ "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()) //Act+Assert at isOk cuz this function return boolean
                .andReturn();

        verify(customerService, times(1)).deleteCustomer(reqId);

    }

    @DisplayName("Test delete customer should return fail")
    @Test
    void testDeleteCustomerFail() throws Exception {
        //Arrange
        Long reqId = 4L;
        when(customerService.deleteCustomer(reqId)).thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer+ "/" + reqId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()) //Act+Assert at isNotFound cuz this function return boolean
                .andReturn();

        verify(customerService, times(1)).deleteCustomer(reqId);

    }
}
