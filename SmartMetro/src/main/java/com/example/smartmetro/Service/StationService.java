package com.example.smartmetro.Service;

import com.example.smartmetro.ApiResponse.ApiException;
import com.example.smartmetro.Model.Station;
import com.example.smartmetro.Repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public void addStation(Station station) {
        stationRepository.save(station);
    }

    public void updateStation(Integer id, Station station) {
        Station oldStation = stationRepository.findStationById(id);
        if (station == null)
            throw new ApiException("Station with ID: " + id + " was not found");

        oldStation.setName(station.getName());
        oldStation.setType(station.getType());
        oldStation.setLocation(station.getLocation());
        oldStation.setStatus(station.getStatus());
        stationRepository.save(oldStation);
    }

    public void deleteStation(Integer id) {
        Station station = stationRepository.findStationById(id);
        if (station == null)
            throw new ApiException("Station with ID: " + id + " was not found");

        stationRepository.delete(station);
    }
    // CRUD - END

    // Getters
    public Station getStationById(Integer id) {
        Station station = stationRepository.findStationById(id);
        if (station == null)
            throw new ApiException("Station with ID: " + id + " was not found");

        return station;
    }

    public Station getStationByName(String name) {
        Station station = stationRepository.findStationByName(name);
        if (station == null)
            throw new ApiException("Station was not found");

        return station;
    }

    public List<Station> getStationByType(String type) {
        List<Station> stations = stationRepository.findStationByType(type);
        if (stations.isEmpty())
            throw new ApiException("There are no station in this type");

        return stations;
    }

    public List<Station> getStationByLocation(String location) {
        List<Station> stations = stationRepository.findStationByLocation(location);
        if (stations.isEmpty())
            throw new ApiException("There are no stations in this location");

        return stations;
    }

    public List<Station> getStationByStatus(String status) {
        List<Station> stations = stationRepository.findStationByStatus(status);
        if (stations.isEmpty())
            throw new ApiException("There are no " + status + " stations");

        return stations;
    }
}
