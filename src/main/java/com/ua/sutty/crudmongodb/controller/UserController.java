package com.ua.sutty.crudmongodb.controller;

import com.ua.sutty.crudmongodb.form.UserForm;
import com.ua.sutty.crudmongodb.model.User;
import com.ua.sutty.crudmongodb.repository.UserRepository;
import com.ua.sutty.crudmongodb.validator.EmailValidator;
import com.ua.sutty.crudmongodb.validator.UsernameValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    private final EmailValidator emailValidator;

    private final UsernameValidator usernameValidator;

    public UserController(UserRepository userRepository, EmailValidator emailValidator,
                          UsernameValidator usernameValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.usernameValidator = usernameValidator;
    }

    @InitBinder("userForm")
    public void addBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
        binder.addValidators(usernameValidator);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userRepository.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserForm userForm) {
        if (userForm == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(userForm);
        User user = userForm.toUser();
        System.out.println(user);
        this.userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user, @PathVariable String id) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setId(id);
        this.userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String  id) {
        User user = this.userRepository.findUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userRepository.deleteUserById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
