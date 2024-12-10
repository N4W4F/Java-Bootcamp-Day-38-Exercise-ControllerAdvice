package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Integer> {

    Journey findJourneyById(Integer id);

    List<Journey> findJourneyByUserId(Integer id);

    @Query("select j from Journey j where j.totalCost > ?1")
    List<Journey> getJourneyGreaterCost(Double cost);

    @Query("select j from Journey j where j.totalCost < ?1")
    List<Journey> getJourneySmallerCost(Double cost);

    @Query("SELECT j FROM Journey j WHERE j.startTime > :currentTime")
    List<Journey> findUpcomingJourneys(@Param("currentTime") LocalDateTime currentTime);

}