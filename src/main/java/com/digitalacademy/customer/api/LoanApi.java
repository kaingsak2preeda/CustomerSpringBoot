package com.digitalacademy.customer.api;

import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.util.Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class LoanApi {
    private RestTemplate restTemplate;

    @Value("${spring.loan.host}")
    private String host;

    @Value("${spring.loan.endpoint.getInfo}")
    private String getInfo;

    public LoanApi(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GetLoanInfoResponse getLoanInfo(Long id) throws Exception{
        String data = "{}";
        try {
            RequestEntity requestEntity = RequestEntity.get(URI.create(host + "/" + getInfo + "/" + id)).build();

            System.out.println("Request: " + requestEntity.getMethod() + " URL: " + requestEntity.getUrl());

            ResponseEntity<String> response = restTemplate.exchange(requestEntity,String.class);

            System.out.println("Response body:" + response.getBody());

            if(response.getStatusCode().value() == 200){
                JSONObject resp = new JSONObject(response.getBody());
                JSONObject status = new JSONObject(resp.getString("status"));

                System.out.println("Status: " + status);

                if(status.getString("code").equals("0")){
                    data = resp.get("data").toString();
                    System.out.println("Data: " + data);
                }
            }

        }catch (final HttpClientErrorException httpClientErrorException){
            System.out.println("httpClientErrorException: " + httpClientErrorException);
            throw new Exception("httpClientErrorException");
        }catch (HttpServerErrorException httpServerErrorException){
            System.out.println("httpServerErrorException: " + httpServerErrorException);
            throw new Exception("httpServerErrorException");
        }catch (Exception ex){
            throw ex;
        }
        return Util.readValue(data, GetLoanInfoResponse.class);
    }
}
