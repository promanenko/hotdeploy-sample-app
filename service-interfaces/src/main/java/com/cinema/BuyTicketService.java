package com.cinema;

import com.cinema.entity.Ticket;
import org.openspaces.remoting.Routing;

/**
 * Created by Anna_Babich on 14.08.2014.
 */
public interface BuyTicketService {
    public void buyTicket(@Routing("getIdMovie")Ticket ticket);
}
