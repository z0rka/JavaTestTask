package com.ururu.security.controller;

import com.ururu.security.dto.ContactDto;
import com.ururu.security.service.ContactService;
import com.ururu.security.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class UserController {
    private final ContactService contactService;

    @PostMapping("")
    public String addContact(@RequestBody Contact contact){
        contactService.addContact(contact);
        return "Added new contact";
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Integer id){
        contactService.deleteContact(id);
        return "Deleted contact";
    }

    @PutMapping("/{id}")
    public String updateContact(@RequestBody Contact contact){
        contactService.update(contact);
        return "Contact updated";
    }

    @GetMapping("")
    public List<ContactDto> getContacts(){
        return contactService.getContacts();
    }
}

