package com.example.cinemaproject.repository;

import com.example.cinemaproject.dto.SeatStatusDTO;
import com.example.cinemaproject.entity.ShowTimeEntity;
import com.example.cinemaproject.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByShowtimeId(Long showtimeId);
    Optional<TicketEntity> findByShowtimeIdAndSeatIdAndStatusIn(Long showtimeId, Long seatId, List<String> statuses);
    List<TicketEntity> findByStatusAndCreatedAtBefore(String status, LocalDateTime dateTime);
    Optional<TicketEntity> findByIdAndStatus(Long id, String status);
}
