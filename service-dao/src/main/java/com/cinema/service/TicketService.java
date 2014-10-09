package com.cinema.service;

import com.cinema.dao.MovieDao;
import com.cinema.dao.TicketDao;
import com.cinema.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TicketService {

    @Autowired
    @Qualifier(value = "gsTicket")
    TicketDao ticketDao;

    @Autowired
    @Qualifier(value = "gsMovie")
    MovieDao movieDao;


    @Transactional
    public void delete(Integer id) {
       movieDao.freeSeat(ticketDao.getTicket(id).getIdMovie());
       ticketDao.deleteTicket(id);
    }

    public Ticket getTicket(Integer id) {
        return ticketDao.getTicket(id);
    }






}
