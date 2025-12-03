package com.smartbus.controller;

import com.smartbus.entity.Contact;
import com.smartbus.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/save")
    public ResponseEntity<?> saveContact(@RequestBody Contact contact) {

        // Check if email already exists
        Optional<Contact> existingEmail = contactRepository.findByEmail(contact.getEmail());

        if (existingEmail.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Email already exists!"));
        }

        // Save new contact message
        Contact savedContact = contactRepository.save(contact);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Message submitted successfully!",
                        "contact", savedContact
                )
        );
    }
}





