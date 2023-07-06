package com.alibou.security.repository;


import com.alibou.security.model.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author ururu 04.07.2023
 */
public interface ContactRepository extends CrudRepository<Contact, Integer> {
    Optional<Contact> findFirstByContactName(String name);

    List<Contact> findAll();
}
