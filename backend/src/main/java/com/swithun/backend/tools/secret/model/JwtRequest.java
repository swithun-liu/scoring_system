/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:25:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 14:31:10
 */
package com.swithun.backend.tools.secret.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	private Integer usertype;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String username, String password, Integer usertype) {
		this.setUsername(username);
		this.setPassword(password);
		this.setUsertype(usertype);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}