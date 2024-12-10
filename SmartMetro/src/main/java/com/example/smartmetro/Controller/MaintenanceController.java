package com.example.smartmetro.Controller;

import com.example.smartmetro.ApiResponse.ApiResponse;
import com.example.smartmetro.Model.Maintenance;
import com.example.smartmetro.Service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/get")
    public ResponseEntity getAllMaintenances() {
        return ResponseEntity.status(200).body(maintenanceService.getAllMaintenances());
    }

    @PostMapping("/add")
    public ResponseEntity addMaintenance(@RequestBody @Valid Maintenance maintenance) {
        maintenanceService.addMaintenance(maintenance);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance schedule added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMaintenance(@PathVariable Integer id, @RequestBody @Valid Maintenance maintenance) {
        maintenanceService.updateMaintenance(id, maintenance);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance schedule updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMaintenance(@PathVariable Integer id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance schedule deleted successfully"));
    }

    @GetMapping("/get-by-station")
    public ResponseEntity getMaintenanceByStationAndTime(@RequestParam Integer stationId,
                                                         @RequestParam String startTime,
                                                         @RequestParam String endTime) {
        List<Maintenance> maintenances = maintenanceService.getMaintenanceByStationAndTime(
                stationId, LocalDateTime.parse(startTime), LocalDateTime.parse(endTime));
        return ResponseEntity.status(200).body(maintenances);
    }
}
