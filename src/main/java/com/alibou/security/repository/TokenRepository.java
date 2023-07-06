package com.alibou.security.repository;

import java.util.List;
import java.util.Optional;

import com.alibou.security.model.Token;
import com.alibou.security.model.User;
import org.springframework.data.repository.CrudRepository;


public interface TokenRepository extends CrudRepository<Token, Integer> {
  List<Token> findAllByUserAndExpiredFalse(User user);
  Optional<Token> findFirstByTokenVal(String token);
}
