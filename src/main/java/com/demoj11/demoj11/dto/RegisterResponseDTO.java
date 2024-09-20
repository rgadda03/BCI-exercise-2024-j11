package com.demoj11.demoj11.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDTO {

	private UUID id; //UUID s232s-sadas2-2321sads-etc
	private LocalDate created;
	private LocalDate lastLogin;
	private String token;
	@JsonProperty("isActive")
	private boolean isActive;


}
