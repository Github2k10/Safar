package com.safar.controller;

import com.safar.exception.AdminException;
import com.safar.exception.UserException;
import com.safar.model.User;
import com.safar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safar")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserException {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/user/update")
    public  ResponseEntity<User> updateUser(@Valid @RequestBody User user, @RequestParam(required = false) String key) throws UserException {
        User updatedUser = userService.updateUser(user, key);
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/user/delete/{userId}")
    public  ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId, @RequestParam(required = false) String key) throws UserException, AdminException {
        User deletedUser= userService.deleteUser(userId, key);
        return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}")
    public  ResponseEntity<User> viewUserById(@PathVariable("userId") Integer userId, @RequestParam(required = false) String key) throws UserException, AdminException {
        User user= userService.viewUserById(userId, key);
        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }

    @GetMapping("/admin/user/all")
    public  ResponseEntity<List<User>> viewAllUser(@RequestParam(required = false) String key) throws UserException, AdminException {
        List<User> list = userService.viewAllUser(key);
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);

    }

}
