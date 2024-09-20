package com.demoj11.demoj11.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "phones")
public class PhoneEntity implements Serializable{

	private static final long serialVersionUID = 4088727029680737417L;

	@Id
	@Column (name = "phone_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID phone_id;

	@Column (name = "number")
	private Long number;

	@Column (name = "city_code")
	private int city_code;

	@Column (name = "country_code")
	private String country_code;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Override
	public String toString() {
		return "PhoneEntity [phone_id=" + phone_id + ", number=" + number + ", city_code=" + city_code
				+ ", country_code=" + country_code + "]";
	}


}
