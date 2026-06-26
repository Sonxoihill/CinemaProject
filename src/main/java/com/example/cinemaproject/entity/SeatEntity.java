package com.example.cinemaproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;
}
