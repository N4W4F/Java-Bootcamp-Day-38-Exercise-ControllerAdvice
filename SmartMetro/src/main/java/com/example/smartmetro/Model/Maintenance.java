package com.example.smartmetro.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Maintenance Station ID cannot be empty")
    private Integer stationId;

    @Column(columnDefinition = "datetime not null")
    @NotNull(message = "Maintenance Start Time cannot be empty")
    private LocalDateTime startTime;

    @Column(columnDefinition = "datetime not null")
    @NotNull(message = "Maintenance End Time cannot be empty")
    private LocalDateTime endTime;

    @Column(columnDefinition = "varchar(100)")
    private String reason;
}

