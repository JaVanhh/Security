package com.demo.secutity.service.impl;

import com.demo.secutity.entity.Role;
import com.demo.secutity.entity.User;
import com.demo.secutity.repository.RoleRepository;
import com.demo.secutity.repository.UserRepository;
import com.demo.secutity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Page<User> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page,5);
        var res = userRepository.findAll(pageable);
        return res;
    }

    @Override
    public HttpStatus update(User user, int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(u -> {
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setUserName(user.getUsername());
            userRepository.save(u);
            return HttpStatus.OK;
        }).orElse(HttpStatus.NOT_FOUND);
    }

    public HttpStatus delete(int userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return HttpStatus.OK;
        }else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus add(User user, String roleName){
        if(user != null){
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);
            userRepository.save(user);
            return HttpStatus.OK;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }

}
