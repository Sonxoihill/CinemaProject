package com.example.cinemaproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "showtimes")
@Data
public class ShowTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "ticket_price", nullable = false)
    private double ticketPrice;
}
