package com.digitalacademy.customer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255, message = "Pls types first name btw 1-255")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255, message = "Pls types first name btw 1-255")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 255, message = "Pls types first name btw 1-255")
    private String email;

    @NotNull
    private String phoneNo;

    private Integer age;

    
}
