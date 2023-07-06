package com.alibou.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author ururu 04.07.2023
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "my_app")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contactName;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "contact",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Email> emailList;

    @OneToMany(mappedBy = "contact",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Phone> phoneList;
}
