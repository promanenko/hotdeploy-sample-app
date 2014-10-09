package com.cinema.processor;

import com.cinema.BuyTicketService;
import com.cinema.entity.Status;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.client.SQLQuery;
import com.cinema.entity.Movie;
import com.cinema.entity.Ticket;
import org.openspaces.core.GigaSpace;
import org.openspaces.remoting.RemotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anna_Babich on 14.08.2014.
 * Event driven remoting.
 */
@RemotingService
public class BuyTicketServiceImp implements BuyTicketService {

    @Autowired
    GigaSpace gigaSpace;

    @Override
    public void buyTicket(Ticket ticket) {
        System.out.println(ticket);
        if (isExist(ticket)||(!isCorrectSeatNumber(ticket))) throw  new IllegalArgumentException();
        ticket.setStatus(Status.VALID);
        gigaSpace.write(ticket);
        takeSeat(ticket);
    }

    /**
     * Check ticket uniqueness for the certain movie.
     *
     * @param ticket
     * @return true, if ticket is unique
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isExist(Ticket ticket) {
        return gigaSpace.read(new SQLQuery<Ticket>(Ticket.class, "idMovie=? and seatNumber=?", ticket.getIdMovie(), ticket.getSeatNumber())) != null;
    }

    /**
     * Check whether the seat number in the range.
     *
     * @param ticket
     * @return true, if seat number is possible.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isCorrectSeatNumber(Ticket ticket) {
        Movie movie = gigaSpace.readById(Movie.class, ticket.getIdMovie());
        int totalSeats = movie.getTotalSeats();
        return totalSeats >= ticket.getSeatNumber();
    }

    public void takeSeat(Ticket ticket) {
        IdQuery<Movie> idQuery = new IdQuery<Movie>(Movie.class, ticket.getIdMovie());
        gigaSpace.change(idQuery, new ChangeSet().decrement("freeSeats", 1));
    }
}

