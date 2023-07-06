package com.ururu.security.service;

import com.ururu.security.auth.AuthenticationRequest;
import com.ururu.security.auth.AuthenticationResponse;
import com.ururu.security.auth.RegisterRequest;
import com.ururu.security.dto.UserDto;
import com.ururu.security.model.Token;
import com.ururu.security.repository.TokenRepository;
import com.ururu.security.model.TokenType;
import com.ururu.security.model.User;
import com.ururu.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final UserService userService;

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public AuthenticationResponse register(RegisterRequest request) {

    log.info("register  invoked");

    User user = new User();
    user.setLogin(request.getLogin());
    user.setPassword(request.getPassword());

    userService.addUser(user);

    UserDto userDto = new UserDto();
    userDto.setLogin(request.getLogin());
    userDto.setPassword(request.getPassword());

    String jwtToken = jwtService.generateToken(userDto);

    saveUserToken(user, jwtToken);

    log.info("register  ended");
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

    UserDto user = userService.getByLogin(request.getLogin());

    String jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);

    User user1 = new User();
    user.setLogin(user.getLogin());
    user.setPassword(user.getPassword());

    saveUserToken(user1, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    Token token = Token.builder()
        .user(user)
            .tokenVal(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();

    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserDto user) {

    Optional<User> firstByLogin = userRepository.findFirstByLogin(user.getLogin());


    List<Token> validUserTokens = tokenRepository.findAllByUserAndExpiredFalse(firstByLogin.orElse(new User()));
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
