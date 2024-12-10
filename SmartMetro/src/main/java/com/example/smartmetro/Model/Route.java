package com.example.smartmetro.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Route Start Station ID cannot be empty")
    private Integer startStationId;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Route End Stations ID cannot be empty")
    private Integer endStationId;

    @Column(columnDefinition = "varchar(5) not null")
    @NotEmpty(message = "Route Transport Type cannot be empty")
    @Pattern(regexp = "^(bus|metro)$", message = "Route Transport Type must be either 'Bus' or 'Metro'")
    private String transportType;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Route Duration Minutes cannot be empty")
    private Integer durationMinutes;

    @Column(columnDefinition = "decimal(5,2) not null")
    @NotNull(message = "Route Price cannot be empty")
    private Double price;
}
