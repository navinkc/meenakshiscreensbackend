package com.meenakshiscreens.meenakshiscreensbackend.controller;

import com.meenakshiscreens.meenakshiscreensbackend.entity.User;
import com.meenakshiscreens.meenakshiscreensbackend.entity.request.UserRequest;
import com.meenakshiscreens.meenakshiscreensbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableHypermediaSupport(type = { EnableHypermediaSupport.HypermediaType.HAL })
@RequestMapping(value = "/api/private/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUsers(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) {
        Page<User> users = userService.getUsers(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return new ResponseEntity<>(String.format("User with %s id not found.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        if (userService.findExistingUserByEmail(userRequest.getEmail())) {
            return new ResponseEntity<String>(String.format("User with %s email already exists.", userRequest.getEmail()), HttpStatus.CONFLICT);
        } else if (userService.findExistingUserByUserName(userRequest.getUserName())) {
            return new ResponseEntity<String>(String.format("User with %s user name already exists.", userRequest.getUserName()), HttpStatus.CONFLICT);
        }
        User createUser = new User();
        createUser.setUserName(userRequest.getUserName());
        createUser.setUserPass(userRequest.getUserPass());
        createUser.setEmail(userRequest.getEmail());
        createUser.setFirstName(userRequest.getFirstName());
        if ((!ObjectUtils.isEmpty(userRequest.getLastName()) || userRequest.getLastName() != null) || (!ObjectUtils.isEmpty(userRequest.getContactNo()) || userRequest.getContactNo() != null)) {
            createUser.setLastName(userRequest.getLastName());
            createUser.setContactNo(userRequest.getContactNo());
        }
        userService.saveUser(createUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
