package com.ururu.security.repository;

import java.util.List;
import java.util.Optional;

import com.ururu.security.model.Token;
import com.ururu.security.model.User;
import org.springframework.data.repository.CrudRepository;


public interface TokenRepository extends CrudRepository<Token, Integer> {
  List<Token> findAllByUserAndExpiredFalse(User user);
  Optional<Token> findFirstByTokenVal(String token);
}
