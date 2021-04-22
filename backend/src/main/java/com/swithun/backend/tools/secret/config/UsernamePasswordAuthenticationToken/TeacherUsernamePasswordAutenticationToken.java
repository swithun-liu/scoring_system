/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-21 16:46:07
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 17:03:56
 */
package com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TeacherUsernamePasswordAutenticationToken extends UsernamePasswordAuthenticationToken{

	public TeacherUsernamePasswordAutenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
    
}
