/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 17:16:12
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 16:54:06
 */
package com.swithun.backend.tools.secret.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.swithun.backend.tools.secret.config.DaoAutenticationProvider.StudentDaoAutenticationProvider;
import com.swithun.backend.tools.secret.config.DaoAutenticationProvider.TeacherDaoAutenticationProvider;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  // 两个 UserDetailsService

  @Autowired
  @Qualifier("JwtStudentUserDetailsService")
  private UserDetailsService JwtStudentUserDetailsService;

  @Autowired
  @Qualifier("JwtTeacherUserDetailsService")
  private UserDetailsService jwtTeacherUserDetailsService;

  // 注入两个 DaoAutenticationProvider

  @Bean("StudentDaoAutenticationProvider ")
  DaoAuthenticationProvider daoStudentDaoAutenticationProvider() {
    return new StudentDaoAutenticationProvider(passwordEncoderHH(), JwtStudentUserDetailsService);
  }

  @Bean("TeacherDaoAutenticationProvider")
  DaoAuthenticationProvider daoTeacherDaoAutenticationProvider() {
    return new TeacherDaoAutenticationProvider(passwordEncoderHH(), jwtTeacherUserDetailsService);
  }

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  // PasswordEncode选择使用官方推荐的 BCryptPasswordEncoder
  @Bean
  public PasswordEncoder passwordEncoderHH() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    // 向AuthenticationManager添加Provider
    auth.authenticationProvider(daoStudentDaoAutenticationProvider());
    auth.authenticationProvider(daoTeacherDaoAutenticationProvider());


  }


  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin().usernameParameter("username") // 自定义登陆用户名参数名
        .passwordParameter("password") // 自定义登陆密码参数名
        // .successHandler(myAuthenticationSuccessHandler)
    ;

    httpSecurity.csrf().disable() // 关掉CSRS
        // .cors(Customizer.withDefaults())
        .cors()

        .and()

        .authorizeRequests() // 授权配置

        // 先写那些应该被放行

        .antMatchers("/authenticate", "/register", "/login", "/student/studentuploadpaper").permitAll()// these requests need not to be authenticated

        .antMatchers("/student/*").hasRole("student")// only students access /student/*

        .antMatchers("/teacher/*").hasRole("teacher")// only teachers access /teacher/*

        // .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // PreFlightRequest(预请求) need not to be
        //                                                             // authenticated

        // 然后写（其他）所有的都应该被认证
        .anyRequest().authenticated()
        
        .and() // all other requests need to be authenticated

        // make sure we use stateless session; session won't be used tostore user's
        // state.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        
        ;

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    // UsernamePasswordAnthenticationFileter 过滤器链
    // 把自己定义的jwtRequestFilter放到最前面
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*"); // 应设置为前端服务器域名前缀 ， 暂时设置为 *
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTION", "HAHA"));
    configuration.addAllowedHeader("*");// 这里很重要，起码需要允许 Access-Control-Allow-Origin
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
