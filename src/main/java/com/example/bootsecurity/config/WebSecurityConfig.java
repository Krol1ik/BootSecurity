package com.example.bootsecurity.config;

import com.example.bootsecurity.model.User;
import com.example.bootsecurity.repository.UserRepository;
import com.example.bootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //Подключает аннотацию для контроля роли в "UserController"
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //Включаем авторизацию
                    .antMatchers("/", "/registration", "/static/**", "/activate/*", "/h2-console/**").permitAll()  //указываем, для каких страниц есть доступ у всех
                    .anyRequest().authenticated()  //а для всех остальных запросов мы требуем авторизацию
                .and()
                    .formLogin()  // включаем форму Login (из нашего шаблона MvcConfig
                    .loginPage("/login")  // указываем, что форма Login находится по данномк мепингу
                    .permitAll()  // указываем, что формой логина могут пользоваться все
                .and()
                    .logout()  // выход из аккаунта
                    .permitAll();  // также указываем, что могут пользоваться все

        http.csrf().disable();
        http.headers().frameOptions().disable();    //Команды, благодоря которым открывается консоль h2
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());      //настроили проверку паролей при логине
        .passwordEncoder(passwordEncoder);


        User admin = userRepository.findByUsername("admin");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));     //весь код для кодирвоки существующих паролей
        User user = userRepository.findByUsername("user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(admin);
        userRepository.save(user);
    }
}
