package com.cinema.dao.gs;

import com.cinema.entity.Status;
import com.cinema.entity.Ticket;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cinema.dao.TicketDao;
import com.j_spaces.core.client.SQLQuery;

@Component("gsTicket")
public class TicketDaoGs implements TicketDao{
	
	@Autowired
	GigaSpace gigaSpace;

	public void addTicket(Ticket ticket) {
		gigaSpace.write(ticket);
	}

	public void deleteTicketsByMovieId(Integer idMovie) {
		Ticket ticket = new Ticket();
		ticket.setIdMovie(idMovie);
		gigaSpace.clear(ticket);
	}

	public void deleteTicket(Integer id) {
		Ticket template = new Ticket();
		template.setIdTicket(id);
		gigaSpace.clear(template);
	}

	public Ticket getTicket(Integer id) {
		return gigaSpace.readById(Ticket.class, id);
	}

	public boolean isExistTicket(Integer idMovie, Integer seatNumber) {
		return gigaSpace.read(new SQLQuery<Ticket>(Ticket.class, "idMovie=? and seatNumber=? and status=?", idMovie, seatNumber, Status.VALID)) != null;
	}
}
