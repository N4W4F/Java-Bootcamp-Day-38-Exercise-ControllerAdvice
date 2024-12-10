package com.example.smartmetro.Service;

import com.example.smartmetro.ApiResponse.ApiException;
import com.example.smartmetro.Model.Route;
import com.example.smartmetro.Model.Station;
import com.example.smartmetro.Repository.RouteRepository;
import com.example.smartmetro.Repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public void addRoute(Route route) {
        Station startStation = stationRepository.findStationById(route.getStartStationId());
        if (startStation == null)
            throw new ApiException("Start Station was not found");

        Station endStation = stationRepository.findStationById(route.getEndStationId());
        if (endStation == null)
            throw new ApiException("End Station was not found");

        routeRepository.save(route);
    }

    public void updateRoute(Integer id, Route route) {
        Route oldRoute = routeRepository.findRouteById(id);
        if (oldRoute == null)
            throw new ApiException("Route with given ID was not found");

        Station startStation = stationRepository.findStationById(route.getStartStationId());
        if (startStation == null)
            throw new ApiException("Start Station was not found");

        Station endStation = stationRepository.findStationById(route.getEndStationId());
        if (endStation == null)
            throw new ApiException("End Station was not found");

        oldRoute.setStartStationId(route.getStartStationId());
        oldRoute.setEndStationId(route.getEndStationId());
        oldRoute.setTransportType(route.getTransportType());
        oldRoute.setDurationMinutes(route.getDurationMinutes());
        oldRoute.setPrice(route.getPrice());
        routeRepository.save(oldRoute);
    }

    public void deleteRoute(Integer id) {
        Route route = routeRepository.findRouteById(id);
        if (route == null)
            throw new ApiException("Route with given ID was not found");

        routeRepository.delete(route);
    }
    // CRUD - END

    // Getters
    public Route getRouteById(Integer id) {
        Route route = routeRepository.findRouteById(id);
        if (route == null)
            throw new ApiException("Route with given ID was not found");

        return route;
    }

    public List<Route> getRouteByStartStationId(Integer id) {
        List<Route> routes = routeRepository.findRouteByStartStationId(id);
        if (routes == null)
            throw new ApiException("There are no routes start with this station");

        return routes;
    }
}
