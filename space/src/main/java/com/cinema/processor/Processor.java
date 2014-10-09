package com.cinema.processor;

import com.cinema.entity.Movie;
import com.cinema.entity.Status;
import com.cinema.entity.Ticket;
import com.gigaspaces.client.ChangeSet;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Anna_Babich on 12.08.2014.
 *
*/
@EventDriven @Polling
public class Processor {

    @Autowired
    GigaSpace gigaSpace;

    @EventTemplate
    public Movie cancelMovieTemplate(){
        Movie template = new Movie();
        template.setStatus(Status.CANCELED);
        return template;
    }

    @SpaceDataEvent
    public Movie cancelMovieAction(Movie movie){
        Ticket ticket = new Ticket();
        ticket.setIdMovie(movie.getIdMovie());
        gigaSpace.change(ticket, new ChangeSet().set("status", Status.CANCELED));
        return movie;
    }


}
