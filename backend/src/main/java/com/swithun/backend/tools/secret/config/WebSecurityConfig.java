/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-07 17:16:12
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-18 15:26:44
 */
package com.swithun.backend.tools.secret.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private UserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
  // @Autowired
  // private SecurityHandler myAuthenticationSuccessHandler;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // configure AuthenticationManager so that it knows from where to load
    // user for matching credentials
    // Use BCryptPasswordEncoder
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
  }

  // PasswordEncode选择使用官方推荐的 BCryptPasswordEncoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
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
        .successHandler(myAuthenticationSuccessHandler)
    ;

    httpSecurity.csrf().disable() // 关掉CSRS
        .cors(Customizer.withDefaults())

        .authorizeRequests() // 授权配置

        // 先写那些应该被放行

        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // PreFlightRequest(预请求) need not to be
                                                                    // authenticated

        .antMatchers("/authenticate", "/register", "/login").permitAll()// these requests need not to be authenticated

        // 然后写（其他）所有的都应该被认证
        .antMatchers("/student/*").hasRole("student")// only students access /student/*

        .anyRequest().authenticated()
        
        .and() // all other requests need to be authenticated

        // make sure we use stateless session; session won't be used tostore user's
        // state.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    // UsernamePasswordAnthenticationFileter 过滤器链
    // 把自己定义的jwtRequestFilter放到最前面
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*"); // 应设置为前端服务器域名前缀 ， 暂时设置为 *
    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    configuration.addAllowedHeader("*");// 这里很重要，起码需要允许 Access-Control-Allow-Origin
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
