/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 16:26:44
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-12 21:33:49
 */
package com.swithun.backend.tools.secret.services;

import java.util.ArrayList;

import com.swithun.backend.dao.LoginRepository;
import com.swithun.backend.entity.LoginEntity;
import com.swithun.backend.tools.secret.model.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // if ("javainuse".equals(username)) {
        //     return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
        //             new ArrayList<>());
        // } else {
        //     throw new UsernameNotFoundException("User not found with username: " + username);
        // }

        LoginEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    /**
     * inserting user details
     * 
     * @param user
     * @return
     */
    public LoginEntity save(UserDTO user) {
        LoginEntity newUser = new LoginEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setUsertype(user.getUsertype());
        return userDao.save(newUser);
    }
}