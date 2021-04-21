/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-21 09:53:55
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 10:30:13
 */
package com.swithun.backend.tools.secret.services;

import com.swithun.backend.dao.TeacherRepository;
import com.swithun.backend.entity.TeacherEntity;
import com.swithun.backend.tools.secret.model.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtTeacherUserDetailsService implements UserDetailsService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TeacherEntity user = teacherRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Teacher not fount with username: " + username);
        }
        return new User(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("teacher,ROLE_teacher"));
    }
    
    public TeacherEntity save(UserDTO user) {
        TeacherEntity newUser = new TeacherEntity();
        newUser.setName(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return teacherRepository.save(newUser);
    }
}