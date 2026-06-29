package com.example.cinemaproject.service;

import com.example.cinemaproject.dto.SeatStatusDTO;
import com.example.cinemaproject.entity.SeatEntity;
import com.example.cinemaproject.entity.ShowtimeEntity;
import com.example.cinemaproject.entity.TicketEntity;
import com.example.cinemaproject.repository.SeatRepository;
import com.example.cinemaproject.repository.ShowtimeRepository;
import com.example.cinemaproject.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowtimeService {
    @Autowired
    ShowtimeRepository showtimeRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    TicketRepository ticketRepository;

    public List<SeatStatusDTO> getRoomLayoutWithStatus(Long showtimeId){
        ShowtimeEntity showTime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay suat chieu hop le"));

        List<SeatEntity> allSeats  = seatRepository.findByRoomId(showTime.getRoom().getId());

        List<TicketEntity> bookedTickets = ticketRepository.findByShowtimeId(showtimeId);

        List<Long> bookedSeatIds = bookedTickets.stream()
                .filter(ticket -> "BOOKED".equals(ticket.getStatus()) || "PENDING".equals(ticket.getStatus()))
                .map(ticket -> ticket.getSeat().getId())
                .toList();

        List<SeatStatusDTO> seatStatusList = new ArrayList<>();
        for (SeatEntity seat : allSeats) {
            boolean isAvailable = !bookedSeatIds.contains(seat.getId());

            seatStatusList.add(new SeatStatusDTO(
                    seat.getId(),
                    seat.getSeatNumber(),
                    seat.getType(),
                    isAvailable
            ));
        }
        return seatStatusList;
    }
}
