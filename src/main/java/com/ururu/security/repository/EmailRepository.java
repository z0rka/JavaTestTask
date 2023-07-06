package com.ururu.security.repository;


import com.ururu.security.model.Email;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ururu 04.07.2023
 */
public interface EmailRepository  extends CrudRepository<Email,Integer> {
}
