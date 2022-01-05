package com.nichoudhary.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {

    @Autowired
    private UserDaoService service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }

        //HATEOAS
        //"all-users", SERVER_PATH + "/users (this is hard-coded style, should not follow this step)
        //retrieveAllUsers
        //Creating an entity model wherein we'll add the user which has to be returned + link to all users
        EntityModel<User> resource = EntityModel.of(user.get());
        //This WebMvcLinkBuilder enables us to build link from a method
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    // input -> details of user
    // output -> CREATED & Return the created URI
    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        //CREATED
        // /user/{id}  savedUser.getId();
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(savedUser.getId()).
                toUri();
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id: " + id);
        }
        return userOptional.get().getPosts();
    }



}
