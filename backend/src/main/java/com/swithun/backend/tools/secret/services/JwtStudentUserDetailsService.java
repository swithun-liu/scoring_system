/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 16:26:44
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-21 10:24:43
 */
package com.swithun.backend.tools.secret.services;

import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.tools.secret.model.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JwtStudentUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    /**
     * @description: 通过用户名 在数据库查询是否有该用户
     * @param {*}
     * @return {*}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. 根据username查询数据库
        StudentEntity user = studentRepository.findByName(username);
        // 2.1 如果没有查询到，抛出异常
        if (user == null) {
            throw new UsernameNotFoundException("Student not found with username: " + username);
        }
        // 2.2 如果查询到，返回User(userdetials的实现类) (姓名，加密后的密码，权限&角色)
        // return new User(user.getName(), user.getPassword(), new ArrayList<>());
        // return new User(user.getName(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin,student"));
        return new User(user.getName(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("student,ROLE_student"));

    }

    /**
     * inserting user details
     * 
     * @param user
     * @return
     */
    public StudentEntity save(UserDTO user) {
        StudentEntity newUser = new StudentEntity();
        newUser.setName(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return studentRepository.save(newUser);
    }
}