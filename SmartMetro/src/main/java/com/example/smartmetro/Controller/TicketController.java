package com.example.smartmetro.Controller;

import com.example.smartmetro.ApiResponse.ApiResponse;
import com.example.smartmetro.Model.Ticket;
import com.example.smartmetro.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/get")
    public ResponseEntity getAllTickets() {
        return ResponseEntity.status(200).body(ticketService.getAllTickets());
    }

    @PostMapping("/add")
    public ResponseEntity addTicket(@RequestBody @Valid Ticket ticket) {
        ticketService.addTicket(ticket);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket has been added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTicket(@PathVariable Integer id, @RequestBody @Valid Ticket ticket) {
        ticketService.updateTicket(id, ticket);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket with ID: " + id + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket with ID: " + id + " has been deleted successfully"));
    }
    // CRUD - END

    // Getters
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getTicketById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ticketService.getTicketById(id));
    }

    @GetMapping("/get/by-user/{id}")
    public ResponseEntity getTicketByUserId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ticketService.getTicketByUserId(id));
    }

    @GetMapping("/get/by-route/{id}")
    public ResponseEntity getTicketByRouteId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ticketService.getTicketByRouteId(id));
    }

    @GetMapping("/get/before-date")
    public ResponseEntity getTicketBeforeDate(@RequestBody LocalDateTime date) {
        return ResponseEntity.status(200).body(ticketService.getTicketBeforeDate(date));
    }

    @GetMapping("/get/after-date")
    public ResponseEntity getTicketAfterDate(@RequestBody LocalDateTime date) {
        return ResponseEntity.status(200).body(ticketService.getTicketAfterDate(date));
    }

    @GetMapping("/get/by-purchase-time")
    public ResponseEntity getTicketByPurchaseTime(@RequestBody LocalDateTime date) {
        return ResponseEntity.status(200).body(ticketService.getTicketByPurchaseTime(date));
    }

    @GetMapping("/get/by-status/{status}")
    public ResponseEntity getTicketByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(ticketService.getTicketByStatus(status));
    }

    // Services
    @PostMapping("/purchase/{userId}/{routeId}")
    public ResponseEntity purchaseTicket(@PathVariable Integer userId, @PathVariable Integer routeId) {
        ticketService.purchaseTicket(userId, routeId);
        return ResponseEntity.status(200).body(new ApiResponse("You have purchased a ticket successfully"));
    }

    @PostMapping("/purchase-to-user/{senderId}/{receiverId}/{routeId}")
    public ResponseEntity purchaseTicketToUser(@PathVariable Integer senderId, @PathVariable Integer receiverId, @PathVariable Integer routeId) {
        ticketService.purchaseTicketToUser(senderId, receiverId, routeId);
        return ResponseEntity.status(200).body(new ApiResponse("You have purchased a ticket successfully"));
    }

    @GetMapping("/validity/{userId}/{routeId}")
    public ResponseEntity checkTicketValidity(@PathVariable Integer userId, @PathVariable Integer routeId) {
        return ResponseEntity.status(200).body("The ticket is " + ticketService.checkTicketValidity(userId, routeId));
    }
}