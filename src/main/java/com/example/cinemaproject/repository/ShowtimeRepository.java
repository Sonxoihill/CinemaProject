package com.example.cinemaproject.repository;

import com.example.cinemaproject.entity.ShowTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends JpaRepository<ShowTimeEntity, Long> {
}
