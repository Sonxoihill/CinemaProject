package com.example.cinemaproject.service;

import com.example.cinemaproject.entity.SeatEntity;
import com.example.cinemaproject.entity.ShowTimeEntity;
import com.example.cinemaproject.entity.TicketEntity;
import com.example.cinemaproject.entity.UserEntity;
import com.example.cinemaproject.repository.SeatRepository;
import com.example.cinemaproject.repository.ShowtimeRepository;
import com.example.cinemaproject.repository.TicketRepository;
import com.example.cinemaproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TicketEntity bookSeat(Long showtimeId, Long seatId, Long userId) {
        List<String> activeStatuses = List.of("PENDING", "BOOKED");
        boolean isSeatTaken = ticketRepository
                .findByShowtimeIdAndSeatIdAndStatusIn(showtimeId, seatId, activeStatuses)
                .isPresent();

        if(isSeatTaken){
            throw new RuntimeException("Ghe nay da co nguoi dat hoac dang giu cho");
        }

        ShowTimeEntity showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Khong tim suat chieu"));
        SeatEntity seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay ghe"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung"));

        TicketEntity newTicket = new TicketEntity();
        newTicket.setShowtime(showtime);
        newTicket.setSeat(seat);
        newTicket.setUser(user);
        newTicket.setStatus("PENDING");
        newTicket.setCreatedAt(LocalDateTime.now());

        return  ticketRepository.save(newTicket);

    }

    @Transactional
    public TicketEntity confirmPayment(Long ticketId){
        TicketEntity ticket = ticketRepository.findByIdAndStatus(ticketId, "PENDING")
                .orElseThrow(() -> new RuntimeException("Ve khong ton tai hoac da het han giu cho"));

        ticket.setStatus("BOOKED");
        ticketRepository.save(ticket);
        return ticket;
    }
}
