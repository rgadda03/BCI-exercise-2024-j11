package com.demoj11.demoj11.exception;


import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorValidacionDTO extends SuperErrorException{
	
	private static final long serialVersionUID = 1L;
	
	private Timestamp timestamp;
	private int codigo;
	private String detail;
	
	public ErrorValidacionDTO(int codigo, String detail) {
		super();
		this.codigo = codigo;
		this.detail = detail;
	}
	
	
	
}
