package com.ururu.security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author ururu 04.07.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {
    private int id;
    private String phoneNumber;
    private ContactDto contact;
}
