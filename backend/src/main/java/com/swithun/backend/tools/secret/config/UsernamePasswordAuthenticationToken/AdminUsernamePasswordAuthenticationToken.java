/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 17:19:10
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 17:20:00
 */
package com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AdminUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public AdminUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
