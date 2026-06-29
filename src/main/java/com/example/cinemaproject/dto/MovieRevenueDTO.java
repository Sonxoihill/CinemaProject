package com.example.cinemaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRevenueDTO {
    private String movieTitle;
    private double totalRevenue;
}
