/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 16:59:30
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-01 15:14:23
 */
package com.swithun.backend.tools.secret.Controller;

import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.StudentUsernamePasswordAuthenticationToken;
import com.swithun.backend.tools.secret.config.UsernamePasswordAuthenticationToken.TeacherUsernamePasswordAutenticationToken;
import com.swithun.backend.tools.secret.model.JwtRequest;
import com.swithun.backend.tools.secret.model.JwtResponse;
import com.swithun.backend.tools.secret.model.UserDTO;
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
    private JwtStudentUserDetailsService studentUserDetailsService;

    @Autowired
    private JwtTeacherUserDetailsService teacherUserDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        UserDetails userDetails;

        // 1. 通过名字 UserDetailService 加载 userDetail
        if (authenticationRequest.getUsertype() == 0) { // 如果是学生
            userDetails = studentUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } else {
            userDetails = teacherUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
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
            return ResponseEntity.ok(studentUserDetailsService.save(user));
        } else {
            return ResponseEntity.ok(teacherUserDetailsService.save(user));
        }
    }

    private void authenticate(String username, String password, Integer usertype) throws Exception {
        System.out.println("jwtAuthenticationController # autenticate");
        try {
            if (usertype == 0) {
                // 1.1 如果是学生: 生成 Student的 UsernamePasswordAutenticationToken-> 会由 StudentDaoAutenticationProvider 处理
                authenticationManager.authenticate(new StudentUsernamePasswordAuthenticationToken(username, password));
            } else {
                // 1.2 如果是老师: 生成 Teacher的 UsernamePasswordAutenticationToken-> 会由 TeacherDaoAutenticationProvider 处理
                authenticationManager.authenticate(new TeacherUsernamePasswordAutenticationToken(username, password));
            }
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}