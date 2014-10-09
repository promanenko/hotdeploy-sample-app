package com.cinema.processor;


import com.cinema.DeleteTicketService;
import com.cinema.entity.Movie;
import com.cinema.entity.Ticket;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.remoting.RemotingService;
import org.openspaces.remoting.Routing;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Anna_Babich on 18.08.2014.
 * Executed based remoting.
 */
@RemotingService
public class DeleteTicketServiceImpl implements DeleteTicketService {

    @Autowired
    GigaSpace gigaSpace;

    @Override
    public void deleteTicket(@Routing("getIdMovie") Ticket ticket) {
        Ticket template = new Ticket();
        template.setIdTicket(ticket.getIdTicket());
        gigaSpace.clear(template);
        freeSeat(ticket.getIdMovie());
    }

    public void freeSeat(Integer id){
        IdQuery<Movie> idQuery = new IdQuery<Movie>(Movie.class, id);
        gigaSpace.change(idQuery, new ChangeSet().increment("freeSeats", 1));
    }
}
