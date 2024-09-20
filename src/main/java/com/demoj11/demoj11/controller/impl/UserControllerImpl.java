package com.demoj11.demoj11.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demoj11.demoj11.controller.UserController;
import com.demoj11.demoj11.dto.ErrorValidacionDTO;
import com.demoj11.demoj11.dto.LoginResponseDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.dto.RegisterResponseDTO;
import com.demoj11.demoj11.exception.ErrorGeneralException;
import com.demoj11.demoj11.exception.SuperErrorException;
import com.demoj11.demoj11.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController{
	
	@Autowired
	private UserService userService;

	@Override
	@PostMapping("/sign-up")
	public RegisterResponseDTO signUp(@RequestBody RegisterRequestDTO request) throws SuperErrorException  {
		
		log.info("sign-up - Inicio ControllerImpl");
		
		RegisterResponseDTO response = userService.signUp(request);
		
		log.info("sign-up - Fin ControllerImpl");
		
		return response;
	}

	@Override
	@GetMapping("/login")
	public LoginResponseDTO login(@RequestParam String token) throws SuperErrorException {
		
		log.info("login - Inicio ControllerImpl");
		
		LoginResponseDTO response = userService.login(token);
		
		log.info("login - Fin ControllerImpl");
		
		return response;
	}

}
