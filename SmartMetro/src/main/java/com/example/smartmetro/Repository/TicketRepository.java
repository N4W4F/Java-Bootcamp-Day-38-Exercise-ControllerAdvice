package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t from Ticket t where t.id = ?1")
    Ticket getTicketById(Integer id);

    @Query("select t from Ticket t where t.userId = ?1")
    List<Ticket> getTicketByUserId(Integer userId);

    List<Ticket> findTicketByRouteId(Integer routeId);

    @Query("select t from Ticket t where t.purchaseTime < ?1")
    List<Ticket> getTicketBeforeDate(LocalDateTime date);

    @Query("select t from Ticket t where t.purchaseTime > ?1")
    List<Ticket> getTicketAfterDate(LocalDateTime date);

    List<Ticket> findTicketByPurchaseTime(LocalDateTime date);

    List<Ticket> findTicketByStatus(String status);

    Ticket findTicketByUserIdAndRouteId(Integer userId, Integer routeId);
}
