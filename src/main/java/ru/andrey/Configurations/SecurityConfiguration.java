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
                .authorizeRequests().antMatchers("/messages/*").hasAuthority("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/user").authenticated()
                .and()
                .authorizeRequests().antMatchers("/registration").permitAll()
                .and()
                .authorizeRequests().antMatchers("/org_choice").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

}