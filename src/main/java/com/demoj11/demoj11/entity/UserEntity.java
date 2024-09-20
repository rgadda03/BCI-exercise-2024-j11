package com.demoj11.demoj11.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "users")
public class UserEntity  implements Serializable{

	private static final long serialVersionUID = 4088727029680737417L;

	@Id
	@Column (name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID user_id; //UUID s232s-sadas2-2321sads-etc

	@Column (name = "name")
	@Nullable
	private String name;

	@Column (name = "email")
	private String email;

	@Column (name = "password")
	private String password;

	@Column (name = "created")
	private LocalDate created;

	@Column (name = "last_login")
	private LocalDate last_login;

	@Column (name = "token")
	private String token;

	@Column (name = "isactive")
	private boolean isactive;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "user",fetch = FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	private List<PhoneEntity> phones;

	@Override
	public String toString() {

		String phoneString = "phones= (";

		if (phones!=null) {
			for (Iterator iterator = phones.iterator(); iterator.hasNext();) {
				PhoneEntity phoneEntity = (PhoneEntity) iterator.next();

				phoneString+= phoneEntity.toString()+",";
			}
		}
		phoneString+=") ";

		return "UserEntity [user_id=" + user_id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", created=" + created + ", last_login=" + last_login + ", token=" + token + ", isactive=" + isactive
				+ ", "+ phoneString + "]";
	}



}
