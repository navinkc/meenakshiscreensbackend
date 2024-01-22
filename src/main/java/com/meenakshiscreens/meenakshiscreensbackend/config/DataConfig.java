package com.meenakshiscreens.meenakshiscreensbackend.config;

import com.meenakshiscreens.meenakshiscreensbackend.config.filter.AuthenticationFilter;
import com.meenakshiscreens.meenakshiscreensbackend.config.filter.ExceptionHandlerFilter;
import com.meenakshiscreens.meenakshiscreensbackend.config.filter.JWTAuthorizationFilter;
import com.meenakshiscreens.meenakshiscreensbackend.config.manager.CustomAuthenticationManager;
import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class DataConfig {

    CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/login");
        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll() // Allow anyone to register user
                .antMatchers(HttpMethod.GET, SecurityConstants.API_PATH_ALL).permitAll() // Allow all the get methods
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
