/*
 * @Descripttion: check if request has token
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:25:47
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 17:14:22
 */

package com.swithun.backend.tools.secret.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swithun.backend.tools.secret.services.JwtAdminUserDetailsService;
import com.swithun.backend.tools.secret.services.JwtStudentUserDetailsService;
import com.swithun.backend.tools.secret.services.JwtTeacherUserDetailsService;
import com.swithun.backend.tools.secret.tools.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtStudentUserDetailsService stuUserDetailsS;

    @Autowired
    private JwtTeacherUserDetailsService teaUserDetailsS;

    // @Autowired
    // private JwtAdminUserDetailsService AdmUserDetailsS;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 检查request是否是null
        logger.warn("request is" + (request == null ? "null" : "not null"));

        /**
         * 打印请求中的所有header
         */
        logger.warn("### begin to print header int request ###");
        Enumeration<String> es = request.getHeaderNames();
        boolean isPreReqeust = false;
        if (es != null) {
            while (es.hasMoreElements()) {
                String a = es.nextElement();
                if (a.equals("access-control-request-headers")) {
                    isPreReqeust = true;
                }
                logger.warn(a + "  " + request.getHeader(a));
            }
        }
        logger.warn("### print completely ###");

        logger.warn("request is" + (isPreReqeust ? " " : " not ") + "a preRequest");

        String requestTokenHeader = request.getHeader("authorization");
        logger.warn("requestTokenHeader" + requestTokenHeader);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null
                && (requestTokenHeader.startsWith("bearer ") || requestTokenHeader.startsWith("Bearer "))) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with bearer String");
        }

        // Once we get the token validate it.
        System.out.println("Once we get the token validate it.");
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            System.out.println("JwtRequestFile # 1");
            List<String> userRoles = jwtTokenUtil.getUserTypeFromToken(jwtToken);
            UserDetails userDetails = null;

            // 1. 如果是学生
            if (userRoles.contains("student")) {
                userDetails = this.stuUserDetailsS.loadUserByUsername(username);
            }
            // 1. 如果是学生
            else if (userRoles.contains("teacher")) {
                userDetails = this.teaUserDetailsS.loadUserByUsername(username);
            }
            // 1. 如果是管理员
            // else if (userRoles.contains("admin")) {
            //     userDetails = this.AdmUserDetailsS.loadUserByUsername(username);
            // }
            // if token is valid configure Spring Security to manually set authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
