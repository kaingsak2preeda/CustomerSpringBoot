package com.digitalacademy.customer.api;

import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanApiTest {
    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    LoanApi loanApi;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanApi = new LoanApi(restTemplate);
    }

    @DisplayName("Test get loan info should return loan information")
    @Test
    void getLoanInfoTest() throws Exception{
        //Arrange
        Long reqId = 1L;

        //use ArgumentMatchers to mock URI path any -> null
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
        .thenReturn(this.prepareResponseSuccess());

        //Act
        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);

        //Assert
        assertEquals("1", resp.getId().toString());
        assertEquals("OK", resp.getStatus());
        assertEquals("102-222-2200", resp.getAccountPayable());
        assertEquals("102-222-2200", resp.getAccountReceivable());
        assertEquals(4400000.0, resp.getPrincipalAmount());

        //use verify checked that restTemplate called to use .exchange(...) correctly, and called 1 times
        verify(restTemplate, times(1))
                .exchange(ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any());
    }

    @DisplayName("Test get loan info should return LOAN 4002")
    @Test
    void getLoanInfoLOAN4002Test () throws Exception{
        //Arrange
        Long reqId = 2L;

        //use ArgumentMatchers to mock URI path any -> null
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseEntityLOAN4002());

        //Act
        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);

        //Assert
        assertEquals(null, resp.getId());
        assertEquals(null, resp.getStatus());
        assertEquals(null, resp.getAccountPayable());
        assertEquals(null, resp.getAccountReceivable());
        assertEquals(0, resp.getPrincipalAmount());

    }

    @DisplayName("Test get loan info should return LOAN 4001")
    @Test
    void getLoanInfoLOAN4001Test () throws Exception{
        //Arrange
        Long reqId = 3L;

        //use ArgumentMatchers to mock URI path any -> null
        when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<String>>any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(this.prepareResponseEntityLOAN4001());

        //Act
        GetLoanInfoResponse resp = loanApi.getLoanInfo(reqId);

        //Assert
        assertEquals(null, resp.getId());
        assertEquals(null, resp.getStatus());
        assertEquals(null, resp.getAccountPayable());
        assertEquals(null, resp.getAccountReceivable());
        assertEquals(0, resp.getPrincipalAmount());

    }

    public static ResponseEntity<String> prepareResponseSuccess() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"0\",\n" +
                "        \"message\": \"success\"\n" +
                "    },\n" +
                "    \"data\": {\n" +
                "        \"id\": 1,\n" +
                "        \"status\": \"OK\",\n" +
                "        \"account_payable\": \"102-222-2200\",\n" +
                "        \"account_receivable\": \"102-222-2200\",\n" +
                "        \"principal_amount\": 4400000.0\n" +
                "    }\n" +
                "}");
    }

    public static ResponseEntity<String> prepareResponseEntityLOAN4002() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN4002\",\n" +
                "        \"message\": \"Loan information not found\"\n" +
                "    }\n" +
                "}");
    }

    public static ResponseEntity<String> prepareResponseEntityLOAN4001() {
        return ResponseEntity.ok("{\n" +
                "    \"status\": {\n" +
                "        \"code\": \"LOAN4001\",\n" +
                "        \"message\": \"Cannot get loan information\"\n" +
                "    },\n" +
                "    \"data\": \"Cannot get loan information\"\n" +
                "}");
    }


}
