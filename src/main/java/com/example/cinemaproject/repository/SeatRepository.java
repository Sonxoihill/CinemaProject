package com.example.cinemaproject.repository;

import com.example.cinemaproject.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Integer> {
    List<SeatEntity> findByRoomId(Long roomId);
}
