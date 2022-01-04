package com.nichoudhary.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return service.findOne(id);
    }

    // input -> details of user
    // output -> CREATED & Return the created URI
    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        User saveUser = service.saveUser(user);
    }



}
