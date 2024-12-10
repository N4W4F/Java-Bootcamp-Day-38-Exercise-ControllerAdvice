package com.example.smartmetro.Model;

import jakarta.persistence.*;
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
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "User ID cannot be empty")
    private Integer userId;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Route ID cannot be empty")
    private Integer routeId;

    @Column(columnDefinition = "datetime default current_timestamp")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime purchaseTime;

    @Column(columnDefinition = "varchar(7) default('active')")
    @Pattern(regexp = "^(active|used|expired)$", message = "Ticket Status must be either 'Active', 'Used' or 'Expired'")
    private String status;

    @PrePersist
    protected void onCreate() {
        this.purchaseTime = LocalDateTime.now();
        this.status = "active";
    }
}
