package com.ripgor.ripgor_boot.config;

import com.ripgor.ripgor_boot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Класс с основными настройками секьюрности.
 * Сюда стекается вся логика Security и именно здесь мы ее настраиваем.
 * В методе configure(HttpSecurity) мы разграничиваем доступ на различные запросы
 * в зависимости от ролей.
 * В методе configure(AuthenticationManagerBuilder) мы определяем, собственно, вид,
 * в котором будут хранится данные о пользователях, а также необходимые детали
 * вроде UserService для работы с БД и PasswordEncoder для конфиденциальности паролей.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private SuccessUserHandler successUserHandler;

    @Autowired
    public void setSuccessUserHandler(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // Отключаем csrf-защиту (чтобы не париться)
                .authorizeRequests()    // настройка авторизованных запросов
                    .antMatchers("/admin/**").hasRole("ADMIN")  // На страницу админов пускаем только админов
                    .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")    // страница пользователя может быть и у админа, и у пользователя
                    .antMatchers("/", "/login", "/registration").permitAll()    // В корень и на страницы логина и регистрации пускаем всех
                    .anyRequest().authenticated()   // остальные запросы -- по аутентификации
                .and()
                    .formLogin().loginPage("/login")    // Говорим, что страница логина будет кастомная
                    .successHandler(successUserHandler)     // И прикручиваем к ней логику перенаправления на нужные запросы
                .and()
                    .logout().permitAll();      // на страницу логаута пускаем всех

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
