package com.demoj11.demoj11.Util;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.demoj11.demoj11.dto.PhoneDTO;
import com.demoj11.demoj11.dto.RegisterRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestValidator {

	public String validarRequestSignUp (RegisterRequestDTO requestDTO) {
		final String mensajeInicial = "Error en la validacion de los campos [";
		String response = mensajeInicial;

		//se valida email
		if ( !validarEntrada(requestDTO.getEmail()) ) {
			response+= " email,";
		}

		//se valida email
		if ( !validarEntrada(requestDTO.getPassword()) ) {
			response+= " password,";
		}

		//si phones es null no pasa nada , sino se valida lo de adentro
		if (requestDTO.getPhones() != null) {
			int posicion = 1;
			List<PhoneDTO> phones = requestDTO.getPhones();

			for (Iterator iterator = phones.iterator(); iterator.hasNext();) {
				PhoneDTO phoneRequestDTO = (PhoneDTO) iterator.next();

				final String modelo = " ( phone posicion="+posicion+"-";
				String temp= modelo;

				//se valida countrycode
				if ( !validarEntrada(phoneRequestDTO.getCountrycode()) ) {
					temp+= " countrycode,";
				}

				if (!temp.equals(modelo)) {
					temp+= ",";
					temp = temp.replace(",,", "");
					response+= temp+"),";
				}
				posicion++;
			}
		}


		if (response.equals(mensajeInicial)) {
			return "Valido";
		} else {
			response+= ",";
			response = response.replaceAll(",,", "");
			return response+" ]";
		}

	}

	private Boolean validarEntrada (String request) {
		Boolean respuesta = false;
		if (request != null) {
			if (!request.isEmpty()) {
				respuesta = true;
			}
		}
		return respuesta;
	}

	public boolean validarEmail(String email) {
		boolean result = true;
		String regularExpresion = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{2,}$";

		Pattern pattern = Pattern.compile(regularExpresion);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			result = false;
		}

		return result;
	}

	public String validarPassword(String password) {
		String result = "Valido";

		String regularExpresionLargo = "^.{8,12}$";
		Pattern patternLargo = Pattern.compile(regularExpresionLargo);
		Matcher matcherLargo = patternLargo.matcher(password);

		String regularExpresionTwoDigits = "^(?=(?:[^0-9]*\\d){2}[^0-9]*$).*";
		Pattern patternTwoDigits = Pattern.compile(regularExpresionTwoDigits);
		Matcher matcherTwoDigits = patternTwoDigits.matcher(password);

		String regularExpresionMayus = "^(?=[^A-Z]*[A-Z][^A-Z]*$).*";
		Pattern patternMayus = Pattern.compile(regularExpresionMayus);
		Matcher matcherMayus = patternMayus.matcher(password);

		//mide el largo entre 8 y 12
		if (!matcherLargo.matches()) {
			result = "tiene que tener el largo entre 8 y 12 caracteres";
		} else {
			//se valida que solo tenga 2 digitos
			if (!matcherTwoDigits.matches()) {
				result = "debe tener solo 2 digitos";
			} else {
				//se valida que solo tenga una mayuscula
				if (!matcherMayus.matches()) {
					result = "debe tener solo una mayuscula";
				}
			}
		}

		return result;
	}

}
