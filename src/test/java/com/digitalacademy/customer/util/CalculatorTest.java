package com.digitalacademy.customer.util;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Data
class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String  lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

public class CalculatorTest {

    private final Calculator calculator = new Calculator();
    private final Person person = new Person("Jane","Doe");

    @Test
    void groupAssertions(){
        assertAll("person",
                ()-> assertEquals("Jane", person.getFirstName()),
                ()-> assertEquals("Doe", person.getLastName()));
    }

    @Test
    void testAddMethod(){
        assertEquals(2, calculator.add(1,1));
        assertEquals(0, calculator.add(-1,1));
        assertEquals(0, calculator.add(1,-1));
        assertEquals(-2, calculator.add(-1,-1));
        assertTrue('a'<'b' , () -> "Assertion messages can be lazily evaluated");
    }

    @Test
    void testMultiplyMethod(){
        assertEquals(2, calculator.multiply(2,1));
        assertEquals(-4, calculator.multiply(-2,2));
        assertEquals(-4, calculator.multiply(2,-2));
        assertEquals(1, calculator.multiply(-1,-1));
        assertTrue('a'<'b' , () -> "Assertion messages can be lazily evaluated");
    }

    @Test
    void testDivideMethod(){
        assertEquals(2, calculator.divide(4,2));
        assertEquals(0, calculator.divide(2,4));
        assertEquals(-2, calculator.divide(-4,2));
        assertEquals(0, calculator.divide(2,-4));
        assertEquals(0, calculator.divide(0,2));
        assertTrue('a'<'b' , () -> "Assertion messages can be lazily evaluated");
    }
}
