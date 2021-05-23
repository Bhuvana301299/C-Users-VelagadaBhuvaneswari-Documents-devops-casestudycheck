package com.myapp.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapp.spring.model.Access;
import com.myapp.spring.model.AccessDetails;
import com.myapp.spring.repository.AccessRepository;

@Service
public class AccessDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AccessRepository userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<Access> user = userRepository.findByEmail(email);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return user.map(AccessDetails::new).get();
    }
 
}