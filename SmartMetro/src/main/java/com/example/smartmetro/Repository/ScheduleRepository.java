package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findScheduleById(Integer id);
    List<Schedule> findScheduleByRouteId(Integer id);
    List<Schedule> findScheduleByStatus(String status);
    List<Schedule> findScheduleByDepartureTime(LocalDateTime date);
}
