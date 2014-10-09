package com.cinema.service;

import com.cinema.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.dao.MovieDao;
import com.cinema.dao.TicketDao;

import java.util.Map;

@Component
public class MovieService {
	
	@Autowired
	@Qualifier(value="gsMovie")
	private MovieDao movieDao;
	
	@Autowired
	@Qualifier(value="gsTicket")
	private TicketDao ticketDao;
	
	public void addMovie(Movie movie){
		movieDao.addMovie(movie);
	}
	
	@Transactional
	public void deleteMovie(Integer id){
		movieDao.deleteMovie(id);
	}

	public Movie getMovie(Integer id){
		return movieDao.getMovie(id);
	}
	
	@Transactional
	public void updateMovie(Movie movie) {
		ticketDao.deleteTicketsByMovieId(movie.getIdMovie());
		movieDao.updateMovie(movie);
	}

    /**
     * Update some fields of Movie object.
     * @param map property - value pairs of Movie object
     * @param id id of updating Movie
     */
    public void up(Map<String,Object> map, Integer id){
        movieDao.up(map,id);
    }
}
