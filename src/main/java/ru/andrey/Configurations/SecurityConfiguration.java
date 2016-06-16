package ru.andrey.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/user/*").authenticated()
                .and()
                .authorizeRequests().antMatchers("/allUsers").permitAll()
                .and()
                .authorizeRequests().antMatchers("/myName").permitAll()
                .and()
                .authorizeRequests().antMatchers("/messages/*").authenticated()
                .and()
                .authorizeRequests().antMatchers("/main/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/registration/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/registration").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }

}