package com.task.cafemanager.services.user.impl;

import com.task.cafemanager.data.entities.User;
import com.task.cafemanager.data.repositories.UserRepository;
import com.task.cafemanager.facade.security.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("myUserDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new MyUserDetails(user.getId(), user.getUsername(), user.getPasswordHash(), user.getRole());
    }
}
