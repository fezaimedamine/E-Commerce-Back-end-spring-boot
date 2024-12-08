package com.SpringBootProject.proj1.Entitys;


import javax.persistence.*;

import com.SpringBootProject.enums.userRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table (name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private long id;
    private String email;
    private String password;
    private String name;
    private userRole role;
    @Lob
    //@Column(length = 100000)
    private byte[] image;

}

