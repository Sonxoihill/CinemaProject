package com.example.cinemaproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cinemas")
@Data
public class CinemaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
}
