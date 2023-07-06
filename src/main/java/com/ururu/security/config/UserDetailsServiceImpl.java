package com.ururu.security.config;

import com.ururu.security.dto.UserDto;
import com.ururu.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;


/**
 * @author ururu 06.07.2023
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    /**
     * Load user from database by his username and formatting him into User from spring security
     *
     * @param login - login of the user
     * @return {@link UserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto userDto = userService.getByLogin(login);

        return new User(userDto.getLogin(), userDto.getPassword(), new HashSet<>());
    }
}