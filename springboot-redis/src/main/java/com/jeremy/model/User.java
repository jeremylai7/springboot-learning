package com.jeremy.model;

import java.io.Serializable;

/**
 * @Author: laizc
 * @Date: Created in  2021-11-24
 * @desc:
 */
public class User implements Serializable{

	private String name;

	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
