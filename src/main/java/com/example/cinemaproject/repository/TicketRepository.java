package com.example.cinemaproject.repository;

import com.example.cinemaproject.dto.MovieRevenueDTO;
import com.example.cinemaproject.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select new com.example.cinemaproject.dto.MovieRevenueDTO(m.title, sum(t.showtime.ticketPrice + case when s.type = 'VIP' then 20000 else 0 end))" +
            "from TicketEntity t " +
            "join t.showtime st " +
            "join st.movie m " +
            "join t.seat s " +
            "where t.status = 'BOOKED' " +
            "group by m.title"
    )
    List<MovieRevenueDTO> getRevenueByMovie();
}
