package com.demoj11.demoj11.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoj11.demoj11.converter.JwtTokenUtil;
import com.demoj11.demoj11.dto.LoginResponseDTO;
import com.demoj11.demoj11.dto.PhoneDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;
import com.demoj11.demoj11.dto.RegisterResponseDTO;
import com.demoj11.demoj11.encrypt.jasyptEncryptorConfig;
import com.demoj11.demoj11.entity.PhoneEntity;
import com.demoj11.demoj11.entity.UserEntity;
import com.demoj11.demoj11.exception.ErrorGeneralDTO;
import com.demoj11.demoj11.exception.ErrorValidacionDTO;
import com.demoj11.demoj11.exception.SuperErrorException;
import com.demoj11.demoj11.repository.PhoneRepository;
import com.demoj11.demoj11.repository.UserRepository;
import com.demoj11.demoj11.service.UserService;
import com.demoj11.demoj11.validator.RequestValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RequestValidator requestValidator;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PhoneRepository phoneRepository;
	
	@Override
	public RegisterResponseDTO signUp(RegisterRequestDTO request) throws SuperErrorException {
		
		try {
		
			log.info("sign-up - Inicio ServiceImpl");
			
			//validar request
			String validationRequest = requestValidator.validarRequestSignUp(request);
			if (!validationRequest.equals("Valido")) {
				log.error("sign-up - error al validar request por [{}]", validationRequest);
				throw new ErrorValidacionDTO(1, validationRequest);
			}
			
			//Validar email
			boolean isEmailValido = requestValidator.validarEmail(request.getEmail());
			if (!isEmailValido) {
				log.error("sign-up - error al validar el formato del email");
				throw new ErrorValidacionDTO(2, "El email no tiene formato valido.");
			}
			
			//Validar password
			String validationPassword =  requestValidator.validarPassword(request.getPassword());
			if (!validationPassword.equals("Valido")) {
				log.error("sign-up - error al validar password por [{}]", validationPassword);
				throw new ErrorValidacionDTO(3, validationPassword);
			}
			
			//validar que no exista en la base ese email ya - funcion hecha
			Optional<UserEntity> userEntityOptional =  null;
			try {
				userEntityOptional = userRepository.findByEmail(request.getEmail());
				log.info("sign up -  la busqueda por el email para validar es [{}]", userEntityOptional.toString());
			} catch (RuntimeException e) {
				log.error("sign-up - error al validar si existe el mail en la base -  detalle:[{}]",e.toString());
				throw new ErrorGeneralDTO(3, "error al validar si existe mail en la base - "+e.toString());
			}
			
			if ( userEntityOptional.isPresent() ) {
				//existe el valor
				log.error("sign-up - ese email ya esta registrado");
				throw new ErrorValidacionDTO(4, "Ese email ya esta registrado.");
			}
			
			//encriptar contraseña - funcion hecha
			String passwordEncrypt = jasyptEncryptorConfig.passwordEncryptor().encrypt(request.getPassword());
			log.info("sign up -  passwordEncrypt es [{}]", passwordEncrypt);
			
			//creacion de jwt para siempre - funcion hecha
			String jwt = jwtTokenUtil.generarToken(request.getEmail());
			
			//crear variable
			UserEntity userEntity = UserEntity.builder()
													.name(request.getName())
													.email(request.getEmail())
													.password(passwordEncrypt)
													.created(LocalDate.now())
													.last_login(null)
													.token(jwt)
													.isactive(true)
													.phones(new ArrayList<>())
													.build();
			
			//se guarda en base H2 - funcion hecha
			UserEntity userEntitySaved = null;
			try {
				userEntitySaved = userRepository.save(userEntity);
			} catch (RuntimeException e) {
				log.error("sign-up - error al guardar user -  detalle:[{}]",e.toString());
				throw new ErrorGeneralDTO(5, "error al guardar user - "+e.toString());
			}
			
			PhoneEntity phoneEntity = null;
			if (request.getPhones() != null) {
				List<PhoneDTO> phones = request.getPhones();
				for (Iterator iterator = phones.iterator(); iterator.hasNext();) {
					PhoneDTO phoneRequestDTO = (PhoneDTO) iterator.next();
					
					phoneEntity = PhoneEntity.builder()
												.number(phoneRequestDTO.getNumber())
												.city_code(phoneRequestDTO.getCitycode())
												.country_code(phoneRequestDTO.getCountrycode())
												.user(userEntitySaved)
												.build();
					
					PhoneEntity phoneEntitySaved = phoneRepository.save(phoneEntity);
				}
			}
			log.info("sign-up -  se formo el entity [{}]",userEntity.toString());
			
			
			
			//armar respuesta
			RegisterResponseDTO response = RegisterResponseDTO.builder()
																	.id(userEntitySaved.getUser_id())
																	.created(userEntitySaved.getCreated())
																	.lastLogin(userEntitySaved.getLast_login())
																	.token(userEntitySaved.getToken())
																	.isActive(userEntitySaved.isIsactive())
																	.build();
			
			log.info("sign-up - Fin ServiceImpl");
			
			return response;
		
		} catch (Exception e) {
			log.error("sign-up - error general : [{}] ", e.getMessage());
			throw new ErrorGeneralDTO(0, "error general: "+e.getMessage());
		}
		
		
	}

	@Override
	public LoginResponseDTO login(String token) throws SuperErrorException {
		try {
			
			log.info("login - Inicio ServiceImpl");
			
			//validar que el token sea dif a null y vacio
			if (token == null || token.equals("")) {
				log.error("login - error al validar token por vacio o nulo");
				throw new ErrorValidacionDTO(1, "error al validar token por vacio o nulo");
			}
			
			//validar expiration
			if (jwtTokenUtil.isTokenExpirado(token)) {
				log.error("login - error al validar token por token expirado");
				throw new ErrorValidacionDTO(2, "error al validar token por expirado");
			}
			
			//sacar email
			String email = jwtTokenUtil.getEmailFromJwt(token);
			log.info("login - email obtenido: {}", email);
			
			//conseguir datos dado el email
			Optional<UserEntity> userEntityOptional =  null;
			try {
				userEntityOptional = userRepository.findByEmail(email);
				log.info("login -  la busqueda por el email es [{}]", userEntityOptional.toString());
			} catch (RuntimeException e) {
				log.error("login - error al conseguir informacion del user es -  detalle:[{}]",e.toString());
				throw new ErrorGeneralDTO(3, "error al conseguir informacion del user es "+e.toString());
			}
			
			if (userEntityOptional.isEmpty()) {
				log.error("login - ese email no esta registrado en el sistema");
				throw new ErrorValidacionDTO(4, "ese email no esta registrado en el sistema.");
			}
			
			UserEntity userEntity = userEntityOptional.get();
			if (!userEntity.getToken().equals(token)) {
				log.error("login - el jwt no es mas valido");
				throw new ErrorValidacionDTO(5, "el jwt no es mas valido.");
			}
			
			//conseguir nuevo token
			String jwt = jwtTokenUtil.generarToken(email);
			
			//actualizar base de datos
			try {
				userRepository.actualizarTokenYLastLoginPorEmail(jwt, email, LocalDate.now());
				log.info("login -  update de token exitoso");
			} catch (RuntimeException e) {
				log.error("login - update de token con error: [{}] ", e.getMessage());
				throw new ErrorGeneralDTO(6, "update de token con error: "+e.toString());
			}
			
			//armar respuesta
			StringEncryptor stringEncryptor = jasyptEncryptorConfig.passwordEncryptor();
			LoginResponseDTO response = LoginResponseDTO.builder()
															.id(userEntity.getUser_id())
															.created(userEntity.getCreated())
															.lastLogin(LocalDate.now())
															.token(jwt)
															.isActive(userEntity.isIsactive())
															.name(userEntity.getName())
															.email(userEntity.getEmail())
															.password(stringEncryptor.decrypt(userEntity.getPassword()))
															.phones(convertirEntityAPhone(userEntity.getPhones()))
															.build();
			System.out.println(response.toString());
			
			log.info("login - Fin ServiceImpl");
			
			return response;
			
		} catch (Exception e) {
			log.error("login - error general : [{}] ", e.getMessage());
			throw new ErrorGeneralDTO(0, "error general: "+e.getMessage());
		}	
	}
	
	public List<PhoneDTO> convertirEntityAPhone (List<PhoneEntity> phonesRequest){
		
		List<PhoneDTO> list =  null;
		if (phonesRequest != null) {
			list =  new ArrayList<>();
			for (Iterator iterator = phonesRequest.iterator(); iterator.hasNext();) {
				PhoneEntity phoneEntity = (PhoneEntity) iterator.next();
				
				PhoneDTO temp = PhoneDTO.builder()
											.number(phoneEntity.getNumber())
											.citycode(phoneEntity.getCity_code())
											.countrycode(phoneEntity.getCountry_code())
											.build();
				list.add(temp);
			}
		}
		
		
		
		return list;
	}

}
