package com.ururu.security.service;

import com.ururu.security.dto.ContactDto;
import com.ururu.security.dto.UserDto;
import com.ururu.security.repository.UserRepository;
import com.ururu.security.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author ururu 04.07.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    public static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Method to parse User to UserDTO
     *
     * @param user - user from database
     * @return {@link UserDto} - parsed user
     */
    private UserDto parseUser(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }

    /**
     * Method to add user
     *
     * @param user-   user
     */
    @Transactional
    public UserDto addUser(User user) {
        log.info("Adding user method invoked");

        if (user == null) {
            log.error("Parameter is null");
            return null;
        } else if (userRepository.findFirstByLogin(user.getLogin()).isPresent()) {
            log.error("User with such login already exists!");
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        log.info("Adding user method ended");

        return parseUser(user);
    }

    /**
     * Method to delete user by id
     *
     * @param id - users id
     */
    @Transactional
    public void deleteUser(int id) {
        log.info("Deleting user method invoked");
        userRepository.deleteById(id);
        log.info("Deleting user method ended");
    }

    /**
     * Method to get  user by id
     *
     * @param id - users id
     * @return UserDto
     */
    public UserDto getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return parseUser(user.orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
    }

    /**
     * Method to get  user full info by id
     *
     * @param id - users id
     * @return UserDto
     */
    @Transactional
    public UserDto getUserFullById(int id) {
        Optional<User> user = userRepository.findById(id);

        UserDto userDto = parseUser(user.orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));

        List<ContactDto> contactDto =
                user.orElse(new User()).getContacts().stream()
                        .map(c -> objectMapper.convertValue(c, ContactDto.class))
                        .toList();

        userDto.setContacts(contactDto);

        return userDto;
    }


    /**
     * Method to get  user by login
     *
     * @param login- users login
     * @return UserDto
     */
    @Transactional
    public UserDto getByLogin(String login) {
        Optional<User> user = userRepository.findFirstByLogin(login);
        return  parseUser(user.orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
    }
}
