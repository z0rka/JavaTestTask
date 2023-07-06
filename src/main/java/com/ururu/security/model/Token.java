package com.ururu.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "my_app")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "token_val")
  private String tokenVal;

  @Enumerated(EnumType.STRING)
  @Column(name = "token_type")
  private TokenType tokenType = TokenType.BEARER;

  private boolean revoked;

  private boolean expired;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_user_id")
  private User user;
}
