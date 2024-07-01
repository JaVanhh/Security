package com.demo.secutity.controller;

import com.demo.secutity.entity.User;
import com.demo.secutity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/show")
    public ResponseEntity<?> getAll (@RequestParam(defaultValue = "0",value = "page") Integer page ){
        return ResponseEntity.ok(userService.getAll(page));
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User user){
        return ResponseEntity.ok(userService.update(user, userId));
    }
    @DeleteMapping("/delete/{userId}")
    public HttpStatus delete(@PathVariable int userId){
        return userService.delete(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user, @RequestParam String roleName) {
        HttpStatus status = userService.add(user, roleName);
        return ResponseEntity.status(status).body("User added successfully");
    }
}
