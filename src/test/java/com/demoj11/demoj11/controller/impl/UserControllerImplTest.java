package com.demoj11.demoj11.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.id.UUIDGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demoj11.demoj11.Util.RequestValidator;
import com.demoj11.demoj11.dto.LoginResponseDTO;
import com.demoj11.demoj11.dto.PhoneDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.dto.RegisterResponseDTO;
import com.demoj11.demoj11.entity.PhoneEntity;
import com.demoj11.demoj11.exception.SuperErrorException;
import com.demoj11.demoj11.service.UserService;
import com.demoj11.demoj11.util.GeneradorDatos;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserControllerImplTest {

	@InjectMocks
	UserControllerImpl userControllerImpl;
	
	@Mock
	UserService userService;
	
	@Test
    void signUpOkTestCaso1() throws SuperErrorException {
        RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(GeneradorDatos.generarPhoneRequestDTO1());
        phones.add(GeneradorDatos.generarPhoneRequestDTO2());
        request.setPhones(phones);
        
        RegisterResponseDTO responseToSend = RegisterResponseDTO.builder()
        															.id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
        															.created(LocalDate.now())
        															.lastLogin(null)
        															.token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RAc2FkYXM")
        															.isActive(true)
        															.build();
        
        when(userService.signUp(request)).thenReturn(responseToSend);
        
        RegisterResponseDTO responseReceived = userControllerImpl.signUp(request);
        
        assertEquals(responseToSend, responseReceived);
    }
	
	@Test
    void loginOkTestCaso1() throws SuperErrorException {
		String tokenRequest = "qweqw";
        
		List<PhoneDTO> phones = new ArrayList<>();
        phones.add(GeneradorDatos.generarPhoneRequestDTO1());
        phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		
        LoginResponseDTO responseToSend = LoginResponseDTO.builder()
															.id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
															.created(LocalDate.now())
															.lastLogin(null)
															.token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RAc2FkYXM")
															.isActive(true)
															.name("name")
															.email("email")
															.password("password")
															.phones(phones)
															.build();
        
        when(userService.login(tokenRequest)).thenReturn(responseToSend);
        
        LoginResponseDTO responseReceived = userControllerImpl.login(tokenRequest);
        
        assertEquals(responseToSend, responseReceived);
    }
	
}
