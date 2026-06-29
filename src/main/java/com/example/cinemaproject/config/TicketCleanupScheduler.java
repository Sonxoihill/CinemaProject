package com.example.cinemaproject.config;

import com.example.cinemaproject.entity.TicketEntity;
import com.example.cinemaproject.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TicketCleanupScheduler {
    @Autowired
    TicketRepository ticketRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredTickets(){
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<TicketEntity> expiredTickets = ticketRepository
                .findByStatusAndCreateAtBefore("PENDING", fiveMinutesAgo);

        if(!expiredTickets.isEmpty()){
            System.out.println("Phat hien" +  expiredTickets.size() + "ve giu cho qua han 5 phut");

            for(TicketEntity ticket : expiredTickets){
                ticket.setStatus("CANCELLED");
            }
        }

        ticketRepository.saveAll(expiredTickets);
        System.out.println("Da giai phong cac ghe qua han thanh cong");
    }
}
