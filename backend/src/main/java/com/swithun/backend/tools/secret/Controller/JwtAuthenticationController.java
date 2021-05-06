/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 16:59:30
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 20:03:59
 */
package com.swithun.backend.tools.secret.Controller;

import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.AdminUsernamePasswordAuthenticationToken;
import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.StudentUsernamePasswordAuthenticationToken;
import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.TeacherUsernamePasswordAutenticationToken;
import com.swithun.backend.tools.secret.model.JwtRequest;
import com.swithun.backend.tools.secret.model.JwtResponse;
import com.swithun.backend.tools.secret.model.UserDTO;
import com.swithun.backend.tools.secret.services.JwtAdminUserDetialsService;
import com.swithun.backend.tools.secret.services.JwtStudentUserDetailsService;
import com.swithun.backend.tools.secret.services.JwtTeacherUserDetailsService;
import com.swithun.backend.tools.secret.tools.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtStudentUserDetailsService studentUDS;

    @Autowired
    private JwtTeacherUserDetailsService teacherUDS;

    @Autowired
    private JwtAdminUserDetialsService adminUDS;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        System.out.println("JwtAuthenticationController : '/authentication'");

        UserDetails userDetails;

        // 1. 通过名字 UserDetailService 加载 userDetail
        // 1.1 如果是 学生
        if (authenticationRequest.getUsertype() == 0) {
            userDetails = studentUDS.loadUserByUsername(authenticationRequest.getUsername());
        }
        // 1.2 如果是 老师
        else if (authenticationRequest.getUsertype() == 1) {
            userDetails = teacherUDS.loadUserByUsername(authenticationRequest.getUsername());
        }
        // 1.3 如果是管理员
        else if (authenticationRequest.getUsertype() == 2) {
            userDetails = adminUDS.loadUserByUsername(authenticationRequest.getUsername());
        } else {
            userDetails = null;
        }
        // 2. 验证 前端传来的 名字 和 密码
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword(),
                authenticationRequest.getUsertype());

        // 3. 通过验证后 使用 userDetials 生成token
        final String token = jwtTokenUtil.generateToken(userDetails);

        // 4. 返回附带 token 的 response
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        if (user.getUsertype() == 0) {
            return ResponseEntity.ok(studentUDS.save(user));
        } else if (user.getUsertype() == 1) {
            return ResponseEntity.ok(teacherUDS.save(user));
        } else if (user.getUsertype() == 2) {
            return ResponseEntity.ok(adminUDS.save(user));
        } else {
            return null;
        }
    }

    private void authenticate(String username, String password, Integer usertype) throws Exception {
        System.out.println("jwtAuthenticationController # autenticate");
        try {
            if (usertype == 0) {
                // 1.1 如果是学生: 生成 Student的 UsernamePasswordAutenticationToken-> 会由
                // StudentDaoAutenticationProvider 处理
                System.out.println("authenticate : 是 学生");
                authenticationManager.authenticate(new StudentUsernamePasswordAuthenticationToken(username, password));
            } else if (usertype == 1) {
                // 1.2 如果是老师: 生成 Teacher的 UsernamePasswordAutenticationToken-> 会由
                // TeacherDaoAutenticationProvider 处理
                System.out.println("authenticate : 是 老师");
                authenticationManager.authenticate(new TeacherUsernamePasswordAutenticationToken(username, password));
            } else if (usertype == 2) {
                // 1.2 如果是管理员: 生成 Admin 的 UsernamePasswordAutenticationToken-> 会由
                // AdminDaoAutenticationProvider 处理
                System.out.println("authenticate : 是 管理员");
                authenticationManager.authenticate(new AdminUsernamePasswordAuthenticationToken(username, password));
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}