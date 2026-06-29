package com.example.cinemaproject.dto;

import com.example.cinemaproject.entity.TicketEntity;
import lombok.Data;

import java.util.List;

@Data
public class BookingResponseDTO {
    private List<TicketEntity> tickets;
    private double totalAmount;
}
