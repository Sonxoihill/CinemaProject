package com.example.cinemaproject.controller;

import com.example.cinemaproject.dto.SeatStatusDTO;
import com.example.cinemaproject.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    @Autowired
    ShowtimeService showtimeService;

    @GetMapping("/{id}/seats")
    public List<SeatStatusDTO> getSeats(@PathVariable Long id){
        return showtimeService.getRoomLayoutWithStatus(id);
    }
}
