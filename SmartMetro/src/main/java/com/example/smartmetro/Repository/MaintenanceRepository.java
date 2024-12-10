package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
    Maintenance findMaintenanceById(Integer id);

    @Query("select m from Maintenance m where m.stationId = :stationId and " +
            "((m.startTime <= :endTime and m.endTime >= :startTime))")
    List<Maintenance> findByStationIdAndTimeRange(@Param("stationId") Integer stationId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

}
