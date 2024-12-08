package com.SpringBootProject.proj1.service;

import java.util.ArrayList;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringBootProject.proj1.Entitys.User;
import com.SpringBootProject.proj1.Repositry.UserRepository;

@Service
public class CustomerUserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private  UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findFirstByEmail(username);
        if (user.isEmpty()) throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),user.get().getPassword(),new ArrayList<>());
    }
}
