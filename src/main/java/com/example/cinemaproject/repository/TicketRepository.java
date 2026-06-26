package com.example.cinemaproject.repository;

import com.example.cinemaproject.entity.ShowTimeEntity;
import com.example.cinemaproject.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByShowtimeId(Long showtimeId);
}
