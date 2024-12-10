package com.example.smartmetro.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "User ID cannot be empty")
    private Integer userId;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Start Station ID cannot be empty")
    private Integer startStationId;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "End Station ID cannot be empty")
    private Integer endStationId;

    @Column(columnDefinition = "datetime not null")
    @NotNull(message = "Start Time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Column(columnDefinition = "datetime not null")
    @NotNull(message = "End Time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Column(columnDefinition = "decimal not null")
    @NotNull(message = "Journey Total Cost cannot be empty")
    private Double totalCost;
}
