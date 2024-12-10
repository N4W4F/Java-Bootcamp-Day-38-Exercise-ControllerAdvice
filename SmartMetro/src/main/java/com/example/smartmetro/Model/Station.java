package com.example.smartmetro.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(32) not null unique")
    @NotEmpty(message = "Station Name cannot be empty")
    private String name;

    @Column(columnDefinition = "varchar(16) not null")
    @NotEmpty(message = "Station Type cannot be empty")
    @Pattern(regexp = "^(bus|metro)$", message = "Station Type must be either 'Bus' or 'Metro'")
    private String type;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "Station Location cannot be empty")
    private String location;

    @Column(columnDefinition = "varchar(12) not null")
    @NotEmpty(message = "Station Status cannot be empty")
    @Pattern(regexp = "^(open|closed)", message = "Station Status must be either 'Open' or 'Closed'")
    private String status;

}
