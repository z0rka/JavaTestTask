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
public class ContactDto {
    private int id;
    private String contactName;
    private UserDto user;
    private List<EmailDto> emailList;
    private List<PhoneDto> phoneList;
}
