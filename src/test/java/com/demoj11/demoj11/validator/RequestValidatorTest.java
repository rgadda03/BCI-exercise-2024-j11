package com.demoj11.demoj11.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demoj11.demoj11.dto.PhoneDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.util.GeneradorDatos;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class RequestValidatorTest {
	
	@InjectMocks
	RequestValidator requestValidator;
	
	@Test
    void validarRequestSignUpOkTestCaso1() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail(null);
        request.setPassword(null);
        request.setPhones(null);
        
        
        String responseExpectedOk = "Error en la validacion de los campos [ email, password ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso2() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("");
        request.setPassword(null);
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        request.setPhones(phoneRequestList);
        
        
        String responseExpectedOk = "Error en la validacion de los campos [ email, password ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso3() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail(null);
        request.setPassword("asdsads");
        request.setPhones(null);
        
        
        String responseExpectedOk = "Error en la validacion de los campos [ email ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso4() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail(null);
        request.setPassword("asdsads");
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        request.setPhones(phoneRequestList);
        
        
        String responseExpectedOk = "Error en la validacion de los campos [ email ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso5() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("");
        request.setPhones(null);
        
        
        String responseExpectedOk = "Error en la validacion de los campos [ password ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso6() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword(null);
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ password ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso7() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("asdsads");
        request.setPhones(new ArrayList<>());
        
        
        String responseExpectedOk = "Valido";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso8() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("asdsads");
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Valido";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso9() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword(null);
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO2());
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO3());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ password ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	
	@Test
    void validarRequestSignUpOkTestCaso10() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword(null);
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode("101")
								.build());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ password ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso11() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword(null);
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode(null)
								.build());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ password, ( phone posicion=2- countrycode) ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso12() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword(null);
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode(null)
								.build());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode("")
								.build());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode(null)
								.build());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ password, ( phone posicion=1- countrycode), ( phone posicion=2- countrycode), ( phone posicion=3- countrycode) ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso13() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("asdsads");
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO2());
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO3());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Valido";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	
	@Test
    void validarRequestSignUpOkTestCaso14() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("asdsads");
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode("101")
								.build());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Valido";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso15() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("asdsads");
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(GeneradorDatos.generarPhoneRequestDTO1());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode(null)
								.build());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ ( phone posicion=2- countrycode) ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarRequestSignUpOkTestCaso16() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setName("nombre");
        request.setEmail("asdsads");
        request.setPassword("asdsads");
        
        List<PhoneDTO> phoneRequestList = new ArrayList<>();
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode(null)
								.build());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode("")
								.build());
        phoneRequestList.add(PhoneDTO.builder()
								.number(0)
								.citycode(0)
								.countrycode(null)
								.build());
        request.setPhones(phoneRequestList);
        
        String responseExpectedOk = "Error en la validacion de los campos [ ( phone posicion=1- countrycode), ( phone posicion=2- countrycode), ( phone posicion=3- countrycode) ]";
        
        String response = requestValidator.validarRequestSignUp(request);
        
        assertEquals(responseExpectedOk, response);
    }
	
	@Test
    void validarEmailOkTestCaso1() {
		String mail = "noValido";
		boolean expected = false;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarEmailOkTestCaso2() {
		String mail = "noValido@";
		boolean expected = false;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarEmailOkTestCaso3() {
		String mail = "noValido@asdas";
		boolean expected = false;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarEmailOkTestCaso4() {
		String mail = "noValido@asdas.";
		boolean expected = false;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarEmailOkTestCaso5() {
		String mail = "noValido@asdas!com";
		boolean expected = false;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarEmailOkTestCaso6() {
		String mail = "noVa@lido@asdas.com";
		boolean expected = false;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarEmailOkTestCaso7() {
		String mail = "ejemplo@correo.com";
		boolean expected = true;
        
		boolean response = requestValidator.validarEmail(mail);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso1() {
		String password = "M12sola";
		String expected = "tiene que tener el largo entre 8 y 12 caracteres";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso2() {
		String password = "M12hsola";
		String expected = "Valido";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso3() {
		String password = "Ms12dfhola";
		String expected = "Valido";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso4() {
		String password = "Ms12wsdfhola";
		String expected = "Valido";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso5() {
		String password = "Ms12wssdfhola";
		String expected = "tiene que tener el largo entre 8 y 12 caracteres";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso6() {
		String password = "asdSgh1klñ";
		String expected = "debe tener solo 2 digitos";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso7() {
		String password = "as3sg2jklñ";
		String expected = "debe tener solo una mayuscula";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso8() {
		String password = "a2dSgh1ksñ";
		String expected = "Valido";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso9() {
		String password = "As3Sg2jklñ";
		String expected = "debe tener solo una mayuscula";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
	
	@Test
    void validarPasswordOkTestCaso10() {
		String password = "As3S12jklñ";
		String expected = "debe tener solo 2 digitos";
        
		String response = requestValidator.validarPassword(password);
        
        assertEquals(expected, response);
    }
}
