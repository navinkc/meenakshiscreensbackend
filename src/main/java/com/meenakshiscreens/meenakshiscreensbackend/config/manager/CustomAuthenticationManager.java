package com.meenakshiscreens.meenakshiscreensbackend.config.manager;

import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import com.meenakshiscreens.meenakshiscreensbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getByUserName(authentication.getName());
        if (user == null) {
            user = userService.getByEmail(authentication.getName());
        }
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getUserPass())) {
            throw new BadCredentialsException("Password is incorrect.");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getUserPass());
    }
}
