package com.kenny.microservices.core.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenny.microservices.core.product.utils.exceptions.InvalidInputException;
import com.kenny.microservices.core.product.utils.exceptions.NotFoundException;
import com.kenny.microservices.core.product.utils.http.ControllerExceptionHandler;
import com.kenny.microservices.core.product.utils.http.HttpErrorInfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ExceptionTests {
    @Autowired
    ControllerExceptionHandler exceptionHandler;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName(" Invalid input Empty Constructor")
    public void shouldThrowInvalidInputExceptionOnEmptyConstructor(){
        InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, ()->{
            throw new InvalidInputException();
        });
        assertEquals(invalidInputException.getMessage(), null);
    }

    @Test
    @DisplayName("Invalid Input Message")
    public void shouldThrowInvalidInputExceptionWithMessage(){
        InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, ()->{
            throw new InvalidInputException("InvalidInputException message");
        });
        assertNotNull(invalidInputException.getMessage());
    }

    @Test
    @DisplayName("Not found empty Constructor")
    public void shouldThrowNotFoundExceptionWithEmptyConstructorTest(){
        NotFoundException notFoundException = assertThrows(NotFoundException.class, ()->{
            throw new NotFoundException();
        });
        assertEquals(notFoundException.getMessage(), null);
    }

    @Test
    @DisplayName("Not Found Message")
    public void shouldThrowNotFoundExceptionWithMessageTest(){
        NotFoundException notFoundException = assertThrows(NotFoundException.class, ()->{
            throw new NotFoundException("Appropriate NotFoundException message");
        });
        assertNotNull(notFoundException.getMessage());
    }

    @Test
    @DisplayName("Http Error without constructor")
    public void shouldThrowHttpErrorInfoWithNoConstructorTest(){
        HttpErrorInfo httpErrorInfo = new HttpErrorInfo();
        assertEquals(httpErrorInfo.getHttpStatus(), null);
        assertEquals(httpErrorInfo.getPath(), null);
        assertEquals(httpErrorInfo.getMessage(), null);
    }

    @Test
    @DisplayName("Http Error with constructor")
    public void shouldThrowHttpErrorInfoWithConstructorTest(){
        HttpErrorInfo httpErrorInfo = new HttpErrorInfo(HttpStatus.BAD_REQUEST, "/products/10000", "Product does not exist");
        assertEquals(httpErrorInfo.getHttpStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(httpErrorInfo.getPath(), "/products/10000");
        assertEquals(httpErrorInfo.getMessage(), "Product does not exist");
    }

}
