package com.example.bootsecurity.config;

import com.example.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //Подключает аннотацию для контроля роли в "UserController"
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //Включаем авторизацию
                    .antMatchers("/", "/registration", "/static/**").permitAll()  //указываем, для каких страниц есть доступ у всех
                    .anyRequest().authenticated()  //а для всех остальных запросов мы требуем авторизацию
                .and()
                    .formLogin()  // включаем форму Login (из нашего шаблона MvcConfig
                    .loginPage("/login")  // указываем, что форма Login находится по данномк мепингу
                    .permitAll()  // указываем, что формой логина могут пользоваться все
                .and()
                    .logout()  // выход из аккаунта
                    .permitAll();  // также указываем, что могут пользоваться все
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
