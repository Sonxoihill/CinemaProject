package com.example.cinemaproject.service;

import com.example.cinemaproject.dto.BookingResponseDTO;
import com.example.cinemaproject.dto.MovieRevenueDTO;
import com.example.cinemaproject.entity.*;
import com.example.cinemaproject.repository.*;
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

    @Autowired
    private VoucherRepository voucherRepository;

    @Transactional
    public TicketEntity bookSeat(Long showtimeId, Long seatId, Long userId) {
        List<String> activeStatuses = List.of("PENDING", "BOOKED");
        boolean isSeatTaken = ticketRepository
                .findByShowtimeIdAndSeatIdAndStatusIn(showtimeId, seatId, activeStatuses)
                .isPresent();

        if(isSeatTaken){
            throw new RuntimeException("Ghe nay da co nguoi dat hoac dang giu cho");
        }

        ShowtimeEntity showtime = showtimeRepository.findById(showtimeId)
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

    @Transactional
    public BookingResponseDTO bookMultipleSeats(Long showtimeId, List<Long> seatIds, Long userId) {
        ShowtimeEntity showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Khong tim suat chieu"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nguoi dung"));

        List<TicketEntity> savedTickets = new ArrayList<>();
        double totalAmount = 0;

        List<String> activeStatuses = List.of("PENDING", "BOOKED");
        for (Long seatId : seatIds) {
            boolean isSeatTaken = ticketRepository
                    .findByShowtimeIdAndSeatIdAndStatusIn(showtimeId, seatId, activeStatuses)
                    .isPresent();

            if(isSeatTaken){
                throw new RuntimeException("Ghe nay da co nguoi dat hoac dang giu cho");
            }

            SeatEntity seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Khong tim thay ghe"));
            TicketEntity newTicket = new TicketEntity();
            newTicket.setStatus("PENDING");
            newTicket.setUser(user);
            newTicket.setSeat(seat);
            newTicket.setShowtime(showtime);
            newTicket.setCreatedAt(LocalDateTime.now());

            double currentPrice = showtime.getTicketPrice();
            if("VIP".equals(seat.getType())){
                currentPrice += 20000;
            }
            totalAmount += currentPrice;

            savedTickets.add(newTicket);
        }

            List<TicketEntity> finalSavedTickets = ticketRepository.saveAll(savedTickets);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setTickets(finalSavedTickets);
        bookingResponseDTO.setTotalAmount(totalAmount);

        return bookingResponseDTO;
    }

    public List<MovieRevenueDTO> getMovieRevenueReport(){
        List<MovieRevenueDTO> movieRevenueDTOList = ticketRepository.getRevenueByMovie();
        return movieRevenueDTOList;
    }

    @Transactional
    public BookingResponseDTO bookMultipleSeatsWithVoucher(Long showtimeId, List<Long> seatIds, Long userId, String voucherCode) {
        BookingResponseDTO response = bookMultipleSeats(showtimeId, seatIds, userId);
        double finalAmount = response.getTotalAmount();

        if(voucherCode != null && !voucherCode.trim().isEmpty()){
            VoucherEntity voucher = voucherRepository.findByCode(voucherCode)
                    .filter(v -> v.getExpiryDate().isAfter(LocalDate.now()) || v.getUsageLimit() > 0 )
                    .orElseThrow(() -> new RuntimeException("Ma giam gia khong hop le hoac da het han"));

            if("FIXED".equals(voucher.getType())){
                finalAmount = finalAmount - voucher.getDiscountValue();
            }
            if("PERCENT".equals(voucher.getType())){
                finalAmount = finalAmount - (finalAmount * voucher.getDiscountValue()/100);
            }
            if(finalAmount < 0){
                finalAmount = 0;

            }

            voucher.setUsageLimit(voucher.getUsageLimit() - 1);
            voucherRepository.save(voucher);
        }

        response.setTotalAmount(finalAmount);
        return response;
    }
}
