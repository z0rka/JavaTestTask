package com.alibou.security.service;

import com.alibou.security.dto.ContactDto;
import com.alibou.security.dto.EmailDto;
import com.alibou.security.dto.PhoneDto;
import com.alibou.security.repository.ContactRepository;
import com.alibou.security.repository.EmailRepository;
import com.alibou.security.repository.PhoneRepository;
import com.alibou.security.model.Contact;
import com.alibou.security.model.Email;
import com.alibou.security.model.Phone;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author ururu 04.07.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final ObjectMapper objectMapper;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_NUMBER_PATTERN = "^\\+380\\d{9}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);


    private static final Pattern pattern2 = Pattern.compile(PHONE_NUMBER_PATTERN);

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = pattern2.matcher(phoneNumber);
        return matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private ContactDto parseContact(Contact contact) {

        ContactDto contactDto = objectMapper.convertValue(contact, ContactDto.class);

        List<EmailDto> emailDto;
        emailDto = contact.getEmailList()
                .stream()
                .map(this::parseEmail).toList();


        List<PhoneDto> phoneDto = contact.getPhoneList()
                .stream()
                .map(this::parsePhone).toList();

        contactDto.setEmailList(emailDto);
        contactDto.setPhoneList(phoneDto);

        return contactDto;
    }


    private EmailDto parseEmail(Email email) {
        return objectMapper.convertValue(email, EmailDto.class);
    }

    private PhoneDto parsePhone(Phone phone) {
        return objectMapper.convertValue(phone, PhoneDto.class);
    }

    /**
     * Method to add user
     *
     * @param contact- contact
     */
    @Transactional
    public void addContact(Contact contact) {
        log.info("Adding contact method invoked");

        if (contact == null) {
            log.error("Parameter is null");
            return;
        } else if (contactRepository.findFirstByContactName(contact.getContactName()).isPresent()) {
            log.error("User with such contact already exists!");
            return;
        }

        contact.getEmailList().forEach(email -> {
            if(!isValidEmail(email.getContactEmail())){
                log.error("Not right email");
                throw new IllegalArgumentException("Wrong email");
            }
        });

        contact.getPhoneList().forEach(p -> {
            if(!isValidEmail(p.getPhoneNumber())){
                log.error("Not right phone");
                throw new IllegalArgumentException("Wrong phone");
            }
        });

        emailRepository.saveAll(contact.getEmailList());
        phoneRepository.saveAll(contact.getPhoneList());

        log.info("Adding contact method ended");
    }

    /**
     * Method to delete user by id
     *
     * @param id - users id
     */
    @Transactional
    public void deleteContact(Integer id) {
        log.info("Deleting user method invoked");
        contactRepository.deleteById(id);
        log.info("Deleting user method ended");
    }

    @Transactional
    public List<ContactDto> getContacts() {
        return contactRepository.findAll().stream().map(this::parseContact).toList();
    }
    @Transactional
    public void update(Contact contact) {
        deleteContact(getByName(contact.getContactName()).getId());

        addContact(contact);
    }

    @Transactional
    private Contact getByName(String name){
        Optional<Contact> firstByContactName = contactRepository.findFirstByContactName(name);

        return firstByContactName.orElse(new Contact());
    }

}
