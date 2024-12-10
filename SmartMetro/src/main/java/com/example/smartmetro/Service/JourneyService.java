package com.example.smartmetro.Service;

import com.example.smartmetro.ApiResponse.ApiException;
import com.example.smartmetro.Model.*;
import com.example.smartmetro.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JourneyService {
    private final JourneyRepository journeyRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final RouteRepository routeRepository;
    private final ScheduleRepository scheduleRepository;
    private final StationRepository stationRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    public void addJourney(Journey journey) {
        User user = userRepository.findUserById(journey.getUserId());
        if (user == null)
            throw new ApiException("User with ID: " + journey.getUserId() + " was not found");

        Station startStation = stationRepository.findStationById(journey.getStartStationId());
        if (startStation == null)
            throw new ApiException("Start Station with ID: " + journey.getStartStationId() + " was not found");

        Station endStation = stationRepository.findStationById(journey.getEndStationId());
        if (endStation == null)
            throw new ApiException("End Station with ID: " + journey.getEndStationId() + " was not found");

        journeyRepository.save(journey);
    }

    public void updateJourney(Integer id, Journey journey) {
        Journey oldJourney = journeyRepository.findJourneyById(id);
        if (oldJourney == null)
            throw new ApiException("Journey with ID: " + id + " was not found");

        User user = userRepository.findUserById(journey.getUserId());
        if (user == null)
            throw new ApiException("User with ID: " + journey.getUserId() + " was not found");

        Station startStation = stationRepository.findStationById(journey.getStartStationId());
        if (startStation == null)
            throw new ApiException("Start Station with ID: " + journey.getStartStationId() + " was not found");

        Station endStation = stationRepository.findStationById(journey.getEndStationId());
        if (endStation == null)
            throw new ApiException("End Station with ID: " + journey.getEndStationId() + " was not found");

        oldJourney.setUserId(journey.getUserId());
        oldJourney.setStartStationId(journey.getStartStationId());
        oldJourney.setEndStationId(journey.getEndStationId());
        oldJourney.setStartTime(journey.getStartTime());
        oldJourney.setEndTime(journey.getEndTime());
        oldJourney.setTotalCost(journey.getTotalCost());
        journeyRepository.save(oldJourney);
    }

    public void deleteJourney(Integer id) {
        Journey journey = journeyRepository.findJourneyById(id);
        if (journey == null)
            throw new ApiException("Journey with ID: " + id + " was not found");

        journeyRepository.delete(journey);
    }
    // CRUD - END

    // Getters
    public Journey getJourneyById(Integer id) {
        Journey journey = journeyRepository.findJourneyById(id);
        if (journey == null)
            throw new ApiException("Journey with ID: " + id + " was not found");

        return journey;
    }

    public List<Journey> getJourneyByUserId(Integer id) {
        List<Journey> journeys = journeyRepository.findJourneyByUserId(id);
        if (journeys.isEmpty())
            throw new ApiException("There are no journeys for this users");

        return journeys;
    }



    // Services
    public void bookJourney(Integer ticketId, Journey journey) {
        User user = userRepository.findUserById(journey.getUserId());
        if (user == null)
            throw new ApiException("User with given ID was not found");

        Ticket ticket = ticketRepository.getTicketById(ticketId);
        if (ticket == null)
            throw new ApiException("Ticket with given ID was not found");

        Route route = routeRepository.findRouteById(ticket.getRouteId());
        if (route == null)
            throw new ApiException("Route with give ID was not found");

        if (ticket.getStatus().equalsIgnoreCase("used"))
            throw new ApiException("Ticket is already used");

        if (ticket.getStatus().equalsIgnoreCase("expired"))
            throw new ApiException("Ticket is expired");

        journeyRepository.save(journey);
        ticket.setStatus("used");
        ticketRepository.save(ticket);
    }

    public Journey getBestJourney(Integer userId, Integer startStationId, Integer endStationId, String preference) {
        List<Route> routes;

        if (preference.equalsIgnoreCase("shortest")) {
            routes = routeRepository.findShortestRoutes(startStationId, endStationId);
        } else if (preference.equalsIgnoreCase("cheapest")) {
            routes = routeRepository.findCheapestRoutes(startStationId, endStationId);
        } else {
            throw new ApiException("Invalid preference. Choose 'shortest' or 'cheapest'.");
        }

        if (routes.isEmpty()) {
            throw new ApiException("No routes found between the given stations.");
        }

        Route selectedRoute = routes.get(0);
        Schedule selectedSchedule = scheduleRepository.findScheduleByRouteId(selectedRoute.getId()).get(0);

        Journey bestJourney = new Journey();
        bestJourney.setId(5);
        bestJourney.setUserId(userId);
        bestJourney.setStartStationId(startStationId);
        bestJourney.setEndStationId(endStationId);
        bestJourney.setStartTime(selectedSchedule.getDepartureTime());
        bestJourney.setEndTime(selectedSchedule.getArrivalTime());
        bestJourney.setTotalCost(selectedRoute.getPrice());


        return bestJourney;
    }


    private final EmailService emailService;

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkForDelays() {
        List<Journey> scheduledJourneys = journeyRepository.findUpcomingJourneys(LocalDateTime.now());

        for (Journey journey : scheduledJourneys) {
            if (isAffectedByMaintenance(journey)) {
                sendNotification(journey);
            }
        }
    }

    private boolean isAffectedByMaintenance(Journey journey) {
        List<Maintenance> maintenance = maintenanceRepository.findByStationIdAndTimeRange(
                journey.getStartStationId(),
                journey.getStartTime(),
                journey.getEndTime()
        );

        return !maintenance.isEmpty();
    }

    private void sendNotification(Journey journey) {
        String userEmail = getUserEmail(journey.getUserId());
        String subject = "Delay Notification for Your Scheduled Journey";
        String body = "Dear " + getUserName(journey.getUserId()) + ",\n\nYour journey from Station " +
                journey.getStartStationId() + " to Station " +
                journey.getEndStationId() + " is delayed. Please check the updated schedule.\n\nThank you.";

        emailService.sendEmail(userEmail, subject, body);
    }

    private String getUserEmail(Integer userId) {
        User user = userRepository.findUserById(userId);
        return user.getEmail();
    }

    private String getUserName(Integer userId) {
        User user = userRepository.findUserById(userId);
        return user.getName();
    }
}
