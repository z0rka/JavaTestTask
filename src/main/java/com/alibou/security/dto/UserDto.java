package com.alibou.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ururu 04.07.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String login;
    private String password;
    private List<ContactDto> contacts;
}
