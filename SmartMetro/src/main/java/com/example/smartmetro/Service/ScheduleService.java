package com.example.smartmetro.Service;

import com.example.smartmetro.ApiResponse.ApiException;
import com.example.smartmetro.Model.Schedule;
import com.example.smartmetro.Repository.RouteRepository;
import com.example.smartmetro.Repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final RouteRepository routeRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public void addSchedule(Schedule schedule) {
        if (routeRepository.findRouteById(schedule.getRouteId()) == null)
            throw new ApiException("Route with given ID was not found");

        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Integer id, Schedule schedule) {
        Schedule oldSchedule = scheduleRepository.findScheduleById(id);
        if (oldSchedule == null)
            throw new ApiException("Schedule was not found");

        if (routeRepository.findRouteById(schedule.getRouteId()) == null)
            throw new ApiException("Route with given ID was not found");

        oldSchedule.setRouteId(schedule.getRouteId());
        oldSchedule.setDepartureTime(schedule.getDepartureTime());
        oldSchedule.setArrivalTime(schedule.getArrivalTime());
        oldSchedule.setStatus(schedule.getStatus());
        scheduleRepository.save(oldSchedule);
    }

    public void deleteSchedule(Integer id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule was not found");

        scheduleRepository.delete(schedule);
    }
    // CRUD - End

    // Getters
    public Schedule getScheduleById(Integer id) {
        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (schedule == null)
            throw new ApiException("Schedule was not found");

        return schedule;
    }

    public List<Schedule> getScheduleByRouteId(Integer id) {
        List<Schedule> schedules = scheduleRepository.findScheduleByRouteId(id);
        if (schedules.isEmpty())
            throw new ApiException("There are no schedules on this route");

        return schedules;
    }

    public List<Schedule> getScheduleByStatus(String status) {
        List<Schedule> schedules = scheduleRepository.findScheduleByStatus(status);
        if (schedules.isEmpty())
            throw new ApiException("There are no schedules with this status");

        return schedules;
    }

    public List<Schedule> getScheduleByDepartureTime(LocalDateTime date) {
        List<Schedule> schedules = scheduleRepository.findScheduleByDepartureTime(date);
        if (schedules.isEmpty())
            throw new ApiException("There are no schedules at this date");

        return schedules;
    }
}
