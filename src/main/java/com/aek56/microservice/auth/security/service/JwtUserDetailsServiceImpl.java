package com.aek56.microservice.auth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.aek56.microservice.auth.security.service.SystemService;
import com.aek56.microservice.auth.entity.SysUser;
import com.aek56.microservice.auth.model.security.AuthUserFactory;
//import com.aek56.microservice.auth.security.JwtUserFactory;
//import com.aek56.microservice.auth.security.repository.UserRepository;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

   /* @Autowired
    //private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }*/
	
	/**
     * 系统服务
     */
    @Autowired
    private SystemService systemService;
    
    @Override
    public UserDetails loadUserByUsername(String loginName)
    {
        SysUser user = systemService.getUserByLoginName(loginName);
        
        if (user == null)
        {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
        }
        else
        {
            return AuthUserFactory.create(user);
        }
    }
}
