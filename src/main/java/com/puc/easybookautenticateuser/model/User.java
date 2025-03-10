package com.puc.easybookautenticateuser.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public User(String username, String password, Profile profile) {
    }
}
