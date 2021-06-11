package com.example.authservice.dao.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
@NoArgsConstructor
public class Customer extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int trials;
    private String guiId;
}
