package com.br.jkassner.apiloteria.security;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccountCredentialsDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
}
