package com.example.smartmetro.Controller;

import com.example.smartmetro.ApiResponse.ApiResponse;
import com.example.smartmetro.Model.Journey;
import com.example.smartmetro.Service.JourneyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/journeys")
public class JourneyController {
    private final JourneyService journeyService;

    @GetMapping("/get")
    public ResponseEntity getAllJourneys() {
        return ResponseEntity.status(200).body(journeyService.getAllJourneys());
    }

    @PostMapping("/add")
    public ResponseEntity addJourney(@RequestBody @Valid Journey journey) {
        journeyService.addJourney(journey);
        return ResponseEntity.status(200).body(new ApiResponse("Journey has been added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateJourney(@PathVariable Integer id, @RequestBody @Valid Journey journey) {
        journeyService.updateJourney(id, journey);
        return ResponseEntity.status(200).body(new ApiResponse("Journey has been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteJourney(@PathVariable Integer id) {
        journeyService.deleteJourney(id);
        return ResponseEntity.status(200).body(new ApiResponse("Journey has been deleted successfully"));
    }
    // CRUD - End

    // Getters
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getJourneyById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(journeyService.getJourneyById(id));
    }

    @GetMapping("/get/by-user/{id}")
    public ResponseEntity getJourneyByUserId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(journeyService.getJourneyByUserId(id));
    }

    // Services
    @PostMapping("/book/{ticketId}")
    public ResponseEntity bookJourney(@PathVariable Integer ticketId, @RequestBody @Valid Journey journey) {
        journeyService.bookJourney(ticketId, journey);
        return ResponseEntity.status(200).body(new ApiResponse("You have booked a journey successfully"));
    }

    @GetMapping("/get-best")
    public ResponseEntity getBestJourney(@RequestParam Integer userId,
                                         @RequestParam Integer startStationId,
                                         @RequestParam Integer endStationId,
                                         @RequestParam String preference) {
        Journey bestJourney = journeyService.getBestJourney(userId, startStationId, endStationId, preference);
        return ResponseEntity.ok(bestJourney);
    }
}