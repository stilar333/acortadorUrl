package com.Stilar.acortadorUrl.services;

import com.Stilar.acortadorUrl.models.UserModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServicesImpl implements UserDetailsService {


    @Autowired
    private UserServicesImpl userServices;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel usuario = (UserModel) userServices.findByUsername(username).getValue();
        if (usuario == null) {
            throw new UsernameNotFoundException(
                    username
            );
        }

        return User.withUsername(username)
                .password(usuario.getPassword())
                .roles((String) "no")
                .build();
    }
}
