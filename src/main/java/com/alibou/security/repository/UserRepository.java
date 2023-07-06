package com.alibou.security.repository;


import com.alibou.security.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ururu 04.07.2023
 */
public interface UserRepository  extends CrudRepository<User,Integer> {
    List<User> findAll();

    /**
     * Method to look for user with exact name in database
     *
     * @return List of {@link User}
     */
    Optional<User> findFirstByLogin(String login);
}
