/*
 * @Descripttion: 自定义登陆成功逻辑
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-18 15:08:00
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-18 15:28:46
 */
package com.swithun.backend.tools.secret.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
            System.out.println("MyAuthenticationSuccessHandler : " + "getDetails " + authentication.getDetails());
            System.out.println("MyAuthenticationSuccessHandler : " + "getName " + authentication.getName());
            System.out.print("MyAuthenticationSuccessHandler : " + "getName ");
            for (GrantedAuthority it : authentication.getAuthorities()) {
                System.out.print(it.toString());
            }
            System.out.println();
            System.out.print("MyAuthenticationSuccessHandler : " + "getPrincipal " + authentication.getPrincipal());
    }
    
}
