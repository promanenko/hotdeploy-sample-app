package com.cinema;

import com.cinema.entity.Ticket;
import org.openspaces.remoting.Routing;

/**
 * Created by Anna_Babich on 18.08.2014.
 */
public interface DeleteTicketService {
    public void deleteTicket(@Routing("getIdMovie") Ticket ticket);
}
