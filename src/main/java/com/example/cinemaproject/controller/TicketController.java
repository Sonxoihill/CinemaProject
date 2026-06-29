package com.example.cinemaproject.controller;

import com.example.cinemaproject.dto.BookingResponseDTO;
import com.example.cinemaproject.dto.MovieRevenueDTO;
import com.example.cinemaproject.entity.TicketEntity;
import com.example.cinemaproject.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("{ticketId}/confirm")
    public TicketEntity confirmPayment(@PathVariable Long ticketId){
        TicketEntity ticket = ticketService.confirmPayment(ticketId);
        return ticket;
    }

    @PostMapping("/book-multiple")
    public BookingResponseDTO bookMultiple(@RequestParam Long showtimeId,
                                           @RequestParam List<Long> seatIds,
                                           @RequestParam Long userId){
        BookingResponseDTO bookingResponseDTO = ticketService.bookMultipleSeats(showtimeId,seatIds,userId);
        return bookingResponseDTO;
    }

    @GetMapping("/revenue")
    public List<MovieRevenueDTO> getMovieRevenue(){
        List<MovieRevenueDTO> movieRevenues = ticketService.getMovieRevenueReport();
        return movieRevenues;
    }

    @PostMapping("book-multiple-voucher")
    public BookingResponseDTO bookMultipleWithVoucher(@RequestParam Long showtimeId,
                                                      @RequestParam List<Long> seatIds,
                                                      @RequestParam Long userId,
                                                      @RequestParam(required = false) String voucherCode){
        BookingResponseDTO responseDTO = ticketService.bookMultipleSeatsWithVoucher(showtimeId, seatIds,userId,voucherCode);
        return responseDTO;
    }


}
