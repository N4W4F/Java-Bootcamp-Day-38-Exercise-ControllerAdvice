package com.example.smartmetro.Service;

import com.example.smartmetro.ApiResponse.ApiException;
import com.example.smartmetro.Model.Route;
import com.example.smartmetro.Model.Ticket;
import com.example.smartmetro.Model.User;
import com.example.smartmetro.Repository.RouteRepository;
import com.example.smartmetro.Repository.TicketRepository;
import com.example.smartmetro.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void updateTicket(Integer id, Ticket ticket) {
        Ticket oldTicket = ticketRepository.getTicketById(id);
        if (oldTicket == null)
            throw new ApiException("Ticket with ID: " + id + " was not found");

        if (userRepository.findUserById(ticket.getUserId()) == null)
            throw new ApiException("User with ID: " + ticket.getUserId() + " was not found");

        if (routeRepository.findRouteById(ticket.getRouteId()) == null)
            throw new ApiException("Route with ID: " + ticket.getRouteId() + " was not found");

        oldTicket.setUserId(ticket.getUserId());
        oldTicket.setRouteId(ticket.getRouteId());
        oldTicket.setStatus(ticket.getStatus());
        ticketRepository.save(oldTicket);
    }

    public void deleteTicket(Integer id) {
        Ticket ticket = ticketRepository.getTicketById(id);
        if (ticket == null)
            throw new ApiException("Ticket with ID: " + id + " was not found");

        ticketRepository.delete(ticket);
    }
    // CRUD - END

    // Getters
    public Ticket getTicketById(Integer id) {
        Ticket ticket = ticketRepository.getTicketById(id);
        if (ticket == null)
            throw new ApiException("Ticket with ID: " + id + " was not found");

        return ticket;
    }

    public List<Ticket> getTicketByUserId(Integer userId) {
        List<Ticket> tickets = ticketRepository.getTicketByUserId(userId);
        if (tickets.isEmpty())
            throw new ApiException("There are no tickets with this user");

        return tickets;
    }

    public List<Ticket> getTicketByRouteId (Integer routeId) {
        List<Ticket> tickets = ticketRepository.findTicketByRouteId(routeId);
        if (tickets.isEmpty())
            throw new ApiException("There are no tickets for this route");

        return tickets;
    }

    public List<Ticket> getTicketBeforeDate(LocalDateTime date) {
        List<Ticket> tickets = ticketRepository.getTicketBeforeDate(date);
        if (tickets.isEmpty())
            throw new ApiException("There are no tickets before: " + date);

        return tickets;
    }

    public List<Ticket> getTicketAfterDate(LocalDateTime date) {
        List<Ticket> tickets = ticketRepository.getTicketAfterDate(date);
        if (tickets.isEmpty())
            throw new ApiException("There are no tickets after: " + date);

        return tickets;
    }

    public List<Ticket> getTicketByPurchaseTime(LocalDateTime date) {
        List<Ticket> tickets = ticketRepository.findTicketByPurchaseTime(date);
        if (tickets.isEmpty())
            throw new ApiException("There are no tickets at this date: " + date);

        return tickets;
    }

    public List<Ticket> getTicketByStatus(String status) {
        List<Ticket> tickets = ticketRepository.findTicketByStatus(status);
        if (tickets.isEmpty())
            throw new ApiException("There are no tickets with this status");

        return tickets;
    }

    // Services
    public void purchaseTicket(Integer userId, Integer routeId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        Route route = routeRepository.findRouteById(routeId);
        if (route == null)
            throw new ApiException("Route with ID: " + routeId + " was not found");

        if (user.getBalance() >= route.getPrice()) {
            Ticket ticket = new Ticket();
            ticket.setUserId(userId);
            ticket.setRouteId(routeId);
            ticketRepository.save(ticket);

            user.setBalance(user.getBalance() - route.getPrice());
            userRepository.save(user);
            return;
        }
        throw new ApiException("You don't have enough balance to buy this ticket");
    }

    public void purchaseTicketToUser(Integer senderId, Integer receiverId, Integer routeId) {
        User sender = userRepository.findUserById(senderId);
        if (sender == null)
            throw new ApiException("Sender ID was not found");

        User receiver = userRepository.findUserById(receiverId);
        if (receiver == null)
            throw new ApiException("Receiver ID was not found");

        Route route = routeRepository.findRouteById(routeId);
        if (route == null)
            throw new ApiException("Route ID was not found");

        if (sender.getBalance() >= route.getPrice()) {
            Ticket ticket = new Ticket();
            ticket.setUserId(receiverId);
            ticket.setRouteId(routeId);
            ticketRepository.save(ticket);

            sender.setBalance(sender.getBalance() - route.getPrice());
            userRepository.save(sender);
            return;
        }
        throw new ApiException("You don't have enough balance to buy this ticket");
    }

    public String checkTicketValidity(Integer userId, Integer routeId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User with ID: " + userId + " was not found");

        Route route = routeRepository.findRouteById(routeId);
        if (route == null)
            throw new ApiException("Route with ID: " + routeId + " was not found");

        Ticket ticket = ticketRepository.findTicketByUserIdAndRouteId(userId, routeId);
        if (ticket == null)
            throw new ApiException("Ticket was not found");

        return ticket.getStatus();
    }
}
