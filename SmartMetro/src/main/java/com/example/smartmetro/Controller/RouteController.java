package com.example.smartmetro.Controller;

import com.example.smartmetro.ApiResponse.ApiResponse;
import com.example.smartmetro.Model.Route;
import com.example.smartmetro.Service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routes")
public class RouteController {
    private final RouteService routeService;

    @GetMapping("/get")
    public ResponseEntity getAllRoutes() {
        return ResponseEntity.status(200).body(routeService.getAllRoutes());
    }

    @PostMapping("/add")
    public ResponseEntity addRoute(@RequestBody @Valid Route route) {
        routeService.addRoute(route);
        return ResponseEntity.status(200).body(new ApiResponse("Route has been added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRoute(@PathVariable Integer id, @RequestBody @Valid Route route) {
        routeService.updateRoute(id, route);
        return ResponseEntity.status(200).body(new ApiResponse("Route has been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRoute(@PathVariable Integer id) {
        routeService.deleteRoute(id);
        return ResponseEntity.status(200).body(new ApiResponse("Route has been deleted successfully"));
    }
    // CRUD - End

    // Getters
    @GetMapping("/get/by-id/{id}")
    public ResponseEntity getRouteById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(routeService.getRouteById(id));
    }

    @GetMapping("/get/by-start-station/{id}")
    public ResponseEntity getRouteByStationId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(routeService.getRouteByStartStationId(id));
    }
}
