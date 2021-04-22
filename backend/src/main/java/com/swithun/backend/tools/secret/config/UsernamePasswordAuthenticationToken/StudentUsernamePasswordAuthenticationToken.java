/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-21 16:45:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 16:47:38
 */
package com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class StudentUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{

	public StudentUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
    
}
