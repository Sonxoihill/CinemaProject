package com.example.cinemaproject.controller;

import com.example.cinemaproject.entity.TicketEntity;
import com.example.cinemaproject.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/book")
    public TicketEntity bookTicket(@RequestParam Long showtimeId,
                                   @RequestParam Long seatId,
                                   @RequestParam Long userId){
        return ticketService.bookSeat(showtimeId, seatId,userId);
    }
}
