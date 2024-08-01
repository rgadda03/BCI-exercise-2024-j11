package com.demoj11.demoj11.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.demoj11.demoj11.converter.JwtTokenUtil;
import com.demoj11.demoj11.dto.LoginResponseDTO;
import com.demoj11.demoj11.dto.PhoneDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.dto.RegisterResponseDTO;
import com.demoj11.demoj11.entity.PhoneEntity;
import com.demoj11.demoj11.entity.UserEntity;
import com.demoj11.demoj11.exception.ErrorGeneralDTO;
import com.demoj11.demoj11.exception.ErrorValidacionDTO;
import com.demoj11.demoj11.exception.SuperErrorException;
import com.demoj11.demoj11.repository.PhoneRepository;
import com.demoj11.demoj11.repository.UserRepository;
import com.demoj11.demoj11.util.GeneradorDatos;
import com.demoj11.demoj11.validator.RequestValidator;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	RequestValidator requestValidator;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	JwtTokenUtil jwtTokenUtil;
	
	@Mock
	PhoneRepository phoneRepository;
	
	@Test
    void signUpOkTestCaso1() throws SuperErrorException {
        RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(GeneradorDatos.generarPhoneRequestDTO1());
        phones.add(GeneradorDatos.generarPhoneRequestDTO2());
        request.setPhones(phones);
        String errorExpected = "Error en la validacion de los campos [ email, password ]";
        
        when(requestValidator.validarRequestSignUp(request)).thenReturn(errorExpected);
        
        ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.signUp(request));
        
        assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void signUpOkTestCaso2() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "El email no tiene formato valido.";
		        
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(false);
		    
		ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.signUp(request));
		   
		assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void signUpOkTestCaso3() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "tiene que tener el largo entre 8 y 12 caracteres";
		        
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn(errorExpected);
		    
		ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.signUp(request));
		   
		assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void signUpOkTestCaso4() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "error al validar si existe mail en la base - java.lang.RuntimeException: mensaje de prueba";
		        
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn("Valido");
		when(userRepository.findByEmail(request.getEmail())).thenThrow(new RuntimeException("mensaje de prueba"));
		    
		ErrorGeneralDTO errorGeneral = assertThrows(ErrorGeneralDTO.class, () -> userService.signUp(request));
		   
		assertEquals(errorExpected, errorGeneral.getDetail());
    }
	
	@Test
    void signUpOkTestCaso5() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "Ese email ya esta registrado.";
		
		UserEntity userEntity = UserEntity.builder()
												.user_id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
												.name("jose")
												.email("email1")
												.password("1231sadsa")
												.created(LocalDate.now())
												.last_login(LocalDate.now())
												.token("sadawdssdasdqw")
												.isactive(false)
												.phones(null)
												.build();
		
		Optional<UserEntity> userEntityOptional = Optional.of(userEntity);
        
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn("Valido");
		when(userRepository.findByEmail(request.getEmail())).thenReturn(userEntityOptional);
		    
		ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.signUp(request));
		   
		assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void signUpOkTestCaso6() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "error al validar si existe mail en la base - java.lang.NullPointerException";
		
		Optional<UserEntity> userEntityOptional = null;
        
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn("Valido");
		when(userRepository.findByEmail(request.getEmail())).thenReturn(userEntityOptional);
		    
		ErrorGeneralDTO errorGeneral = assertThrows(ErrorGeneralDTO.class, () -> userService.signUp(request));
		   
		assertEquals(errorExpected, errorGeneral.getDetail());
    }
	
	@Test
    void signUpOkTestCaso7() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "error general: error runtime";
		
		Optional<UserEntity> userEntityOptional = Optional.empty();
        
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn("Valido");
		when(userRepository.findByEmail(request.getEmail())).thenReturn(userEntityOptional);
		when(jwtTokenUtil.generarToken(request.getEmail()))
			.thenThrow(new RuntimeException("error runtime"));
		    
		ErrorGeneralDTO errorGeneral = assertThrows(ErrorGeneralDTO.class, () -> userService.signUp(request));
		   
		assertEquals(errorExpected, errorGeneral.getDetail());
    }
	
	@Test
    void signUpOkTestCaso8() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "wqewqewqewq";
		String passwordEncription = "OY6s4lhoYUSq7P/aGk3S1A==";
		Optional<UserEntity> userEntityOptional = Optional.empty();
		UserEntity userEntity = UserEntity.builder()
				.user_id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
				.name("jose")
				.email("email1")
				.password("1231sadsa")
				.created(LocalDate.now())
				.last_login(LocalDate.now())
				.token("sadawdssdasdqw")
				.isactive(false)
				.phones(null)
				.build();
		
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn("Valido");
		when(userRepository.findByEmail(request.getEmail())).thenReturn(userEntityOptional);
		when(jwtTokenUtil.generarToken(request.getEmail()))
			.thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RAc2FkYXMuY29tIiwiZXhwIjoxNzIyNjY0NTc5LCJpYXQiOjE3MjI0NDg1Nzl");
		when(userRepository.save(userEntity)).thenThrow(new RuntimeException("error runtimeException 2"));
		    
		ErrorGeneralDTO errorGeneral = assertThrows(ErrorGeneralDTO.class, () -> userService.signUp(request));
    }
	
	@Test
    void signUpOkTestCaso9() throws SuperErrorException {
		RegisterRequestDTO request = GeneradorDatos.generarRegisterRequestDTO1();
		List<PhoneDTO> phones = new ArrayList<>();
		phones.add(GeneradorDatos.generarPhoneRequestDTO1());
		phones.add(GeneradorDatos.generarPhoneRequestDTO2());
		request.setPhones(phones);
		String errorExpected = "error en encriptar";
		String passwordEncription = "OY6s4lhoYUSq7P/aGk3S1A==";
		Optional<UserEntity> userEntityOptional = Optional.empty();
		
		UserEntity userEntityFrom = GeneradorDatos.generarUserEntityFrom();
		UserEntity userEntityTo = GeneradorDatos.generarUserEntityTo();
		
		RegisterResponseDTO responseExpected = RegisterResponseDTO.builder()
																.id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
																.created(LocalDate.now())
																.lastLogin(LocalDate.now())
																.token("sadawdssdasdqw")
																.isActive(false)
																.build();
		
		when(requestValidator.validarRequestSignUp(request)).thenReturn("Valido");
		when(requestValidator.validarEmail(request.getEmail())).thenReturn(true);
		when(requestValidator.validarPassword(request.getPassword())).thenReturn("Valido");
		when(userRepository.findByEmail(request.getEmail())).thenReturn(userEntityOptional);
		when(jwtTokenUtil.generarToken(request.getEmail()))
			.thenReturn("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RAc2FkYXMuY29tIiwiZXhwIjoxNzIyNjY0NTc5LCJpYXQiOjE3MjI0NDg1Nzl");
		when(userRepository.save(any())).thenReturn(userEntityTo);
		when(phoneRepository.save(any())).thenReturn(null); // se pone null porque es lo mismo, su valor no se usa para nada
		    
		RegisterResponseDTO response = userService.signUp(request);
		   
		assertEquals(responseExpected, response);
    }
	
	@Test
    void loginOkTestCaso1() throws SuperErrorException {
		String tokenRequest = "";
		String errorExpected = "error al validar token por vacio o nulo";
        
        ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void loginOkTestCaso2() throws SuperErrorException {
		String tokenRequest = null;
		String errorExpected = "error al validar token por vacio o nulo";
        
        ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void loginOkTestCaso3() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "error general: error de ejemplo 1";
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenThrow(new RuntimeException("error de ejemplo 1"));
		
        ErrorGeneralDTO errorGeneralDTO = assertThrows(ErrorGeneralDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorGeneralDTO.getDetail());
    }
	
	@Test
    void loginOkTestCaso4() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "error al validar token por expirado";
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(true);
		
		ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void loginOkTestCaso5() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "error general: error de ejemplo 2";
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenThrow(new RuntimeException("error de ejemplo 2"));
		
		ErrorGeneralDTO errorGeneralDTO = assertThrows(ErrorGeneralDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorGeneralDTO.getDetail());
    }
	
	@Test
    void loginOkTestCaso6() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "error al conseguir informacion del user es java.lang.RuntimeException: error de ejemplo 3";
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenThrow(new RuntimeException("error de ejemplo 3"));
		
		
		ErrorGeneralDTO errorGeneralDTO = assertThrows(ErrorGeneralDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorGeneralDTO.getDetail());
    }
	
	@Test
    void loginOkTestCaso7() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "ese email no esta registrado en el sistema.";
		Optional<UserEntity> userEntiOptional = Optional.empty();
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenReturn(userEntiOptional);
		
		ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void loginOkTestCaso8() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "el jwt no es mas valido.";
		UserEntity userEntity = UserEntity.builder()
											.token("1234")
											.build();
		Optional<UserEntity> userEntiOptional = Optional.of(userEntity);
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenReturn(userEntiOptional);
		
		ErrorValidacionDTO errorValidacion = assertThrows(ErrorValidacionDTO.class, () -> userService.login(tokenRequest));
        
        assertEquals(errorExpected, errorValidacion.getDetail());
    }
	
	@Test
    void loginOkTestCaso9() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "error general: error runtime";
		UserEntity userEntity = UserEntity.builder()
											.token("123")
											.build();
		Optional<UserEntity> userEntiOptional = Optional.of(userEntity);
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenReturn(userEntiOptional);
		when(jwtTokenUtil.generarToken("email1")).thenThrow(new RuntimeException("error runtime"));
	    
		ErrorGeneralDTO errorGeneral = assertThrows(ErrorGeneralDTO.class, () -> userService.login(tokenRequest));
	   
		assertEquals(errorExpected, errorGeneral.getDetail());
    }
	
	@Test
    void loginOkTestCaso10() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "update de token con error: java.lang.RuntimeException: error de ejemplo 4";
		UserEntity userEntity = UserEntity.builder()
											.token("123")
											.build();
		Optional<UserEntity> userEntiOptional = Optional.of(userEntity);
        
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenReturn(userEntiOptional);
		when(jwtTokenUtil.generarToken("email1")).thenReturn("qwe123");
		when(userRepository.actualizarTokenYLastLoginPorEmail("qwe123", "email1", LocalDate.now())).thenThrow(new RuntimeException("error de ejemplo 4"));
		
		ErrorGeneralDTO errorGeneral = assertThrows(ErrorGeneralDTO.class, () -> userService.login(tokenRequest));
	   
		assertEquals(errorExpected, errorGeneral.getDetail());
    }
	
	@Test
    void loginOkTestCaso11() throws SuperErrorException {
		String tokenRequest = "123";
		String errorExpected = "update de token con error: java.lang.RuntimeException: error de ejemplo 4";
		UserEntity userEntity = UserEntity.builder()
											.user_id(UUID.fromString("f434f57b-e70a-4a11-ac96-7093d53e1d66"))
											.name("dasd")
											.email("email1")
											.password("LdQlCFzW/8LR01I8jDNPHeBZTZ06X2gZ")
											.created(LocalDate.now())
											.last_login(LocalDate.now())
											.token("123")
											.isactive(true)		
											.phones(null)
											.build();
		
		Optional<UserEntity> userEntiOptional = Optional.of(userEntity);
        
		LoginResponseDTO responseExpected = LoginResponseDTO.builder()
																.id(UUID.fromString("f434f57b-e70a-4a11-ac96-7093d53e1d66"))
																.created(LocalDate.now())
																.lastLogin(LocalDate.now())
																.token("qwe123")
																.name("dasd")
																.isActive(true)
																.email("email1")
																.password("as2f1hzxSvbn")
																.phones(null)
																.build();
		
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenReturn(userEntiOptional);
		when(jwtTokenUtil.generarToken("email1")).thenReturn("qwe123");
		when(userRepository.actualizarTokenYLastLoginPorEmail("qwe123", "email1", LocalDate.now())).thenReturn(1);
		
		LoginResponseDTO  responseReceived = userService.login(tokenRequest);
	   
		assertEquals(responseExpected, responseReceived);
    }
	
	@Test
    void loginOkTestCaso12() throws SuperErrorException {
		String tokenRequest = "123";
		UserEntity userEntity = UserEntity.builder()
											.user_id(UUID.fromString("f434f57b-e70a-4a11-ac96-7093d53e1d66"))
											.name("dasd")
											.email("email1")
											.password("LdQlCFzW/8LR01I8jDNPHeBZTZ06X2gZ")
											.created(LocalDate.now())
											.last_login(LocalDate.now())
											.token("123")
											.isactive(true)		
											.phones(null)
											.build();
		
		List<PhoneEntity> phoneEntities= new ArrayList<>();
		
		PhoneEntity phoneEnt1 = PhoneEntity.builder()
												.phone_id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
												.city_code(2)
												.country_code("AR")
												.number(1L)
												.user(userEntity)
												.build();
		phoneEntities.add(phoneEnt1);
		
		PhoneEntity phoneEnt2 = PhoneEntity.builder()
				.phone_id(UUID.fromString("c8b7fe6d-597b-47f1-a23e-61abc616d103"))
				.city_code(20)
				.country_code("AR")
				.number(10L)
				.user(userEntity)
				.build();
		phoneEntities.add(phoneEnt2);
		
		userEntity.setPhones(phoneEntities);
		
		Optional<UserEntity> userEntiOptional = Optional.of(userEntity);
        
		List<PhoneDTO> phoneDTOs= new ArrayList<>();
		
		PhoneDTO phoneDTO1 = PhoneDTO.builder()
						.citycode(2)
						.countrycode("AR")
						.number(1)
						.build();
		phoneDTOs.add(phoneDTO1);
		
		PhoneDTO phoneDTO2 = PhoneDTO.builder()
				.citycode(20)
				.countrycode("AR")
				.number(10)
				.build();
		phoneDTOs.add(phoneDTO2);
		
		LoginResponseDTO responseExpected = LoginResponseDTO.builder()
																.id(UUID.fromString("f434f57b-e70a-4a11-ac96-7093d53e1d66"))
																.created(LocalDate.now())
																.lastLogin(LocalDate.now())
																.token("qwe123")
																.name("dasd")
																.isActive(true)
																.email("email1")
																.password("as2f1hzxSvbn")
																.phones(phoneDTOs)
																.build();
		
		when(jwtTokenUtil.isTokenExpirado(tokenRequest)).thenReturn(false);
		when(jwtTokenUtil.getEmailFromJwt(tokenRequest)).thenReturn("email1");
		when(userRepository.findByEmail("email1")).thenReturn(userEntiOptional);
		when(jwtTokenUtil.generarToken("email1")).thenReturn("qwe123");
		when(userRepository.actualizarTokenYLastLoginPorEmail("qwe123", "email1", LocalDate.now())).thenReturn(1);
		
		LoginResponseDTO  responseReceived = userService.login(tokenRequest);
	   
		assertEquals(responseExpected, responseReceived);
    }

	
}
