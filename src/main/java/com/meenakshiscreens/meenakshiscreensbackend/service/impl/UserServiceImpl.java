package com.meenakshiscreens.meenakshiscreensbackend.service.impl;

import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import com.meenakshiscreens.meenakshiscreensbackend.exception.EntityNotFoundException;
import com.meenakshiscreens.meenakshiscreensbackend.repository.UserRepository;
import com.meenakshiscreens.meenakshiscreensbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getByUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return unwrapUser(user, userName);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public void saveUser(User user) {
        user.setUserPass(bCryptPasswordEncoder.encode(user.getUserPass()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean findExistingUserByEmail(String email) {
        boolean success = false;
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser != null) {
            success = true;
        }
        return success;
    }

    @Override
    public boolean findExistingUserByUserName(String userName) {
        boolean success = false;
        User existingUser = userRepository.findByUserName(userName).orElse(null);
        if (existingUser != null) {
            success = true;
        }
        return success;
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }

    static User unwrapUser(Optional<User> entity, String s) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(s, User.class);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return unwrapUser(user, email);
    }
}
