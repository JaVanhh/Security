package com.demo.secutity.service;

import com.demo.secutity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;


public interface UserService {
    Page<User> getAll(Integer page);
    HttpStatus update (User user , int userId);

    HttpStatus delete(int user_id);
    HttpStatus add(User user,String RoleName);
}
