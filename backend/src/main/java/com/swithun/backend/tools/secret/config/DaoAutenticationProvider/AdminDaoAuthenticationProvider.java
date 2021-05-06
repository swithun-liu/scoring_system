/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 17:20:30
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 18:59:03
 */
package com.swithun.backend.tools.secret.config.DaoAutenticationProvider;

import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.AdminUsernamePasswordAuthenticationToken;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminDaoAuthenticationProvider extends DaoAuthenticationProvider {


    public AdminDaoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        setPasswordEncoder(passwordEncoder);
        setUserDetailsService(userDetailsService);
    }

    @Override
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AdminUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}