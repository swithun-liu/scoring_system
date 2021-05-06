/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 19:01:34
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 19:47:47
 */
package com.swithun.backend.tools.secret.services;

import com.swithun.backend.dao.AdminRepository;
import com.swithun.backend.entity.AdminEntity;
import com.swithun.backend.tools.secret.model.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("JwtAdminUserDetailsService")
public class JwtAdminUserDetialsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("AdminUserDetailsService根据username查询数据库");

        AdminEntity admin = adminR.findByName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("没有这个人: " + username);
        }
        return new User(admin.getName(), admin.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_admin"));
    }

    public AdminEntity save(UserDTO user) {
        AdminEntity admin = new AdminEntity();
        admin.setName(user.getUsername());
        PasswordEncoder bcrypEncoder = new BCryptPasswordEncoder();
        admin.setPassword(bcrypEncoder.encode(user.getPassword()));
        return adminR.save(admin);
    }

}
/*
 * @Descripttion:
 * 
 * @version:
 * 
 * @@Company: None
 * 
 * @Author: Swithun Liu
 * 
 * @Date: 2021-05-06 19:01:34
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-05-06 19:01:36
 */
