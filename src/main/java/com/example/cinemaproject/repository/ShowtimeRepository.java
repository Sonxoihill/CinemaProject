package com.example.cinemaproject.repository;

import com.example.cinemaproject.entity.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Long> {
}
