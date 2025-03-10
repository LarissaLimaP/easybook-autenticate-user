package com.puc.easybookautenticateuser.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    private int id;

    @Column
    private String description;

    @JsonCreator
    public Profile(@JsonProperty("id") int id) {
        this.id = id;
    }
}
