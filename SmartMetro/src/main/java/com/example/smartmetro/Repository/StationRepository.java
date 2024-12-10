package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {

    Station findStationById(Integer id);

    Station findStationByName(String name);

    List<Station> findStationByType(String type);

    List<Station> findStationByStatus(String status);

    List<Station> findStationByLocation(String location);
}
