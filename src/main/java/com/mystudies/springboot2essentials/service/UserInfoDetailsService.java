package com.mystudies.springboot2essentials.service;

import com.mystudies.springboot2essentials.exception.BadRequestException;
import com.mystudies.springboot2essentials.repository.UserInfoDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoDetailsService implements UserDetailsService {

    private final UserInfoDetailsRepository userInfoDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(userInfoDetailsRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }
    public UserDetails findById(long id) {
        return userInfoDetailsRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found!"));
    }
}
