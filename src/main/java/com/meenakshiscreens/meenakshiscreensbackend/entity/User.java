package com.meenakshiscreens.meenakshiscreensbackend.entity;

import com.meenakshiscreens.meenakshiscreensbackend.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "User name cannot be blank")
    @NonNull
    @Column(name = "user_name", unique = true)
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @NonNull
    @Column(name = "user_pass")
    private String userPass;

    @NotBlank
    @NonNull
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
}
