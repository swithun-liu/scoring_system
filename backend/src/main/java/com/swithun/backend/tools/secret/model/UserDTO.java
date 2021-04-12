/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 18:45:04
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-12 21:33:41
 */
package com.swithun.backend.tools.secret.model;

public class UserDTO {
	private String username;
	private String password;
	private Integer usertype;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
}
