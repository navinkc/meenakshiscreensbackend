package com.meenakshiscreens.meenakshiscreensbackend.service;

import org.springframework.stereotype.Service;
import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public interface UserService {

    public User getByUserName(String userName);

    public Page<User> getUsers(Pageable pageable);

    public User getById(Long id);

    public void saveUser(User user);

    public void deleteUser(Long id);

    public boolean findExistingUserByEmail(String email);

    public boolean findExistingUserByUserName(String userName);

    public User getByEmail(String email);
}
