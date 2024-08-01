package com.demoj11.demoj11.service;

import com.demoj11.demoj11.dto.LoginResponseDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.dto.RegisterResponseDTO;
import com.demoj11.demoj11.exception.ErrorGeneralException;
import com.demoj11.demoj11.exception.ErrorValidacionDTO;
import com.demoj11.demoj11.exception.SuperErrorException;

public interface UserService {
	
	RegisterResponseDTO signUp (RegisterRequestDTO request) throws SuperErrorException ;
	
	LoginResponseDTO login (String token) throws SuperErrorException;
	
}
