package com.cinema.controller;

import com.cinema.BuyTicketService;
import com.cinema.DeleteTicketService;
import com.cinema.entity.Ticket;
import org.openspaces.remoting.EventDrivenProxy;
import org.openspaces.remoting.ExecutorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cinema.errors.Errors;
import com.cinema.service.TicketService;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;

    @EventDrivenProxy(timeout = 15000)
    private BuyTicketService buyTicketService;

    @ExecutorProxy
    private DeleteTicketService deleteTicketService;

    public void setBuyTicketService(BuyTicketService buyTicketService) {
        this.buyTicketService = buyTicketService;
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void buy(@RequestBody Ticket ticket){
        buyTicketService.buyTicket(ticket);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id){
		Ticket ticket = ticketService.getTicket(id);
        deleteTicketService.deleteTicket(ticket);
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public Ticket getTicket(@PathVariable Integer id){
		return ticketService.getTicket(id);
	}
	
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    @ExceptionHandler(IllegalArgumentException.class)
    public Errors wrongTicket() {
    	Errors errors = new Errors();
    	errors.setMessage("Ticket is not available");
        return errors;
    }
}
