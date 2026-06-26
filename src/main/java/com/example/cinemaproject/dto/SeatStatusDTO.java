package com.example.cinemaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatStatusDTO {
    private Long seatId;
    private String seatNumber;
    private String type;
    private boolean isAvailable;
}
