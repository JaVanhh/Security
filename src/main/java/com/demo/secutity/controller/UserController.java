package com.demo.secutity.controller;

import com.demo.secutity.entity.User;
import com.demo.secutity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(
            description = "SHOW USER",
            summary = "Show user"
    )
    @GetMapping("/show")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "page") Integer page) {
        return ResponseEntity.ok(userService.getAll(page));
    }
    @Operation(
            description = "UPDATE USER",
            summary = "Update user"
    )
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(user, userId));
    }
    @Operation(
            description = "DELETE USER",
            summary = "Delete user"
    )
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable int userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
    @Operation(
            description = "ADD USER",
            summary = "Create new user"
    )
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user, @RequestParam String roleName) {
        HttpStatus status = userService.add(user, roleName);
        return ResponseEntity.status(status).body("User added successfully");
    }
}
