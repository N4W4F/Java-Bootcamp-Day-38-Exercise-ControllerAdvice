package com.example.smartmetro.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(12) not null")
    @NotEmpty(message = "User Name cannot be empty")
    @Size(min = 3, message = "User Name must be 3-12 characters")
    private String name;

    @Column(columnDefinition = "varchar(32) not null unique")
    @NotEmpty(message = "User Email cannot be empty")
    @Email(message = "User Email must be in valid email format")
    private String email;

    @Column(columnDefinition = "varchar(21) not null")
    @NotEmpty(message = "User Password cannot be empty")
    private String password;

    @Column(columnDefinition = "varchar(8) not null")
    @NotEmpty(message = "User Preference cannot empty")
    @Pattern(regexp = "^(shortest|cheapest)$", message = "User Preference must be either 'Shortest' or 'Cheapest'")
    private String preference;

    @NotNull(message = "User Balance cannot be empty")
    @Column(columnDefinition = "decimal not null")
    private Double balance;
}
