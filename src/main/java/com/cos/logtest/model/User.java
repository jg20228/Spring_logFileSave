package com.cos.logtest.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private int id;

	//javax가 들고있는 어노테이션을 찾아야한다.
	@NotBlank(message = "유저네임이 공백일 수 없습니다.")
	@Size(max = 10, message = "10자 초과 했습니다.")//길이
	private String username;

	@NotBlank(message = "패스워드가 공백일 수 없습니다.")
	private String password;

	@NotBlank(message = "이메일이 공백일 수 없습니다.")
	@Email
	private String email;

}
