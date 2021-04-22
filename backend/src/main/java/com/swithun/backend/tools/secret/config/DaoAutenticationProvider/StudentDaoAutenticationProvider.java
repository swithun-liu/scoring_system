/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-21 16:48:30
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 21:28:04
 */
package com.swithun.backend.tools.secret.config.DaoAutenticationProvider;

import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.StudentUsernamePasswordAuthenticationToken;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StudentDaoAutenticationProvider extends DaoAuthenticationProvider {

    /**
     * @description: 之后由 WebSecurityConfig 来 设置 StudentDaoAutenticationProvider 对应
     *               JwtStudentUserDetailsService 从数据库加载对象
     *               TeacherDaoAutenticationProvider 使用 JwtTeacherUserDetailsService
     *               从数据库加载对象
     * @param {PasswordEncoder}            passwordEncoder
     * @param {UserDetailsPasswordService} userDetailsPasswordService
     * @return {*}
     */
    public StudentDaoAutenticationProvider(PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
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

    /**
     * @description: 由WebSecurityConfig 来设置 StudentDaoAuthenticationProvider 处理
     *               StudentDaoAutenticationProvider
     *               TeacherDaoAuthenticationProvider 处理
     *               TeacherDaoAuthenticationProvider
     * @param {*}
     * @return {*}
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return StudentUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}