package com.ururu.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ururu 04.07.2023
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "my_app")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "fk_contact_id", nullable = false)
    private Contact contact;
}
