package com.example.cinemaproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rooms")
@Data
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private CinemaEntity cinema;
}
