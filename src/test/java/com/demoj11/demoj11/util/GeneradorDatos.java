package com.demoj11.demoj11.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.demoj11.demoj11.dto.PhoneDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.entity.PhoneEntity;
import com.demoj11.demoj11.entity.UserEntity;

public class GeneradorDatos {
	
	public static PhoneDTO generarPhoneRequestDTO1() {
		return PhoneDTO.builder()
							.number(12312321)
							.citycode(1)
							.countrycode("101")
							.build();
	}
	
	public static PhoneDTO generarPhoneRequestDTO2() {
		return PhoneDTO.builder()
							.number(45645645)
							.citycode(2)
							.countrycode("101")
							.build();
	}
	
	public static PhoneDTO generarPhoneRequestDTO3() {
		return PhoneDTO.builder()
							.number(9871231)
							.citycode(1)
							.countrycode("101")
							.build();
	}
	
	public static RegisterRequestDTO generarRegisterRequestDTO1() {
		return RegisterRequestDTO.builder()
									.name("nombre")
									.email("email1@sadas.com")
									.password("1sadsadsa")
									.phones(null)
									.build();
	}
	
	public static UserEntity generarUserEntityFrom() {
		List<PhoneEntity> phoneList = new ArrayList<>();
		
		PhoneEntity phone1 = PhoneEntity.builder()
											.phone_id(null)
											.number(12312321L)
											.city_code(1)
											.country_code("101")
											.user(null)
											.build();
		phoneList.add(phone1);
		
		PhoneEntity phone2 = PhoneEntity.builder()
				.phone_id(null)
				.number(45645645L)
				.city_code(2)
				.country_code("101")
				.user(null)
				.build();
		phoneList.add(phone2);
		
		return UserEntity.builder()
				.user_id(null)
				.name("nombre")
				.email("email1@sadas.com")
				.password("LgsphJsdwhvEPj/h30zlA==")
				.created(LocalDate.now())
				.last_login(null)
				.token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RAc2FkYXMuY29tIiwiZXhwIjoxNzIyNjY0NTc5LCJpYXQiOjE3MjI0NDg1Nzl")
				.isactive(true)
				.phones(phoneList)
				.build();
	}
	
	public static UserEntity generarUserEntityTo() {
		return UserEntity.builder()
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
	}
	
}
