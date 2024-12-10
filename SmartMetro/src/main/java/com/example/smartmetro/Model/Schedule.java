package com.example.smartmetro.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Route ID cannot be empty")
    private Integer routeId;

    @Column(columnDefinition = "time not null")
    @NotNull(message = "Departure Time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @Column(columnDefinition = "time not null")
    @NotNull(message = "Arrival Time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    @Column(columnDefinition = "varchar(7) not null")
    @NotEmpty(message = "Schedule Status cannot be empty")
    @Pattern(regexp = "^(on time|delayed)$", message = "Schedule Status must be either 'On Time' or 'Delayed'")
    private String status;
}
