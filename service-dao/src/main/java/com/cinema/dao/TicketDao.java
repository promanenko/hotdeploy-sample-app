package com.cinema.dao;


import com.cinema.entity.Ticket;

public interface TicketDao {
	public void addTicket(Ticket ticket);
	public void deleteTicketsByMovieId(Integer idMovie);
	public void deleteTicket(Integer id);
	public Ticket getTicket(Integer id);
	public boolean isExistTicket(Integer idMovie, Integer seatNumber);

}
