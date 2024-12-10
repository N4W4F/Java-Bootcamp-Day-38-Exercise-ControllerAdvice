package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    Route findRouteById(Integer id);

    List<Route> findRouteByStartStationId(Integer id);

    List<Route> findRouteByEndStationId(Integer id);

    Route findRouteByStartStationIdAndEndStationId(Integer startStationId, Integer endStationId);

    @Query("select r from Route r where r.price = ?1")
    List<Route> getRouteByPrice(Double price);

    @Query("select r from Route r where r.price > ?1")
    List<Route> getRouteGreaterThanPrice(Double price);

    @Query("select r from Route r where r.price < ?1")
    List<Route> getRouteLessThanPrice(Double price);

    List<Route> findRouteByTransportType(String type);

    List<Route> findRouteByDurationMinutesGreaterThanEqual(Integer durationMinutes);

    List<Route> findRouteByDurationMinutesLessThanEqual(Integer durationMinutes);

    @Query("select r from Route r where r.startStationId = ?1 and r.endStationId = ?2 order by r.durationMinutes asc")
    List<Route> findShortestRoutes(Integer startStationId, Integer endStationId);

    @Query("select r from Route r where r.startStationId = ?1 and r.endStationId = ?2 order by r.price asc")
    List<Route> findCheapestRoutes(Integer startStationId, Integer endStationId);

}
