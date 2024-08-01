package com.demoj11.demoj11.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

	private String name; //opcional
	private String email;
	private String password;
	private List<PhoneDTO> phones; //opcional
	
}
