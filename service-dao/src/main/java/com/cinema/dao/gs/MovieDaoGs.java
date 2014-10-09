package com.cinema.dao.gs;

import com.cinema.entity.Status;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.query.IdQuery;
import com.cinema.entity.Movie;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cinema.dao.MovieDao;


import java.io.Serializable;
import java.util.Map;

@Component("gsMovie")
public class MovieDaoGs implements MovieDao{
	
	@Autowired
	private GigaSpace gigaSpace;

	public void addMovie(Movie movie) {
        movie.setStatus(Status.VALID);
		movie.setFreeSeats(movie.getTotalSeats());
		gigaSpace.write(movie);
	}

	public void deleteMovie(Integer id) {
		Movie movie = new Movie();
		movie.setIdMovie(id);
		gigaSpace.change(movie, new ChangeSet().set("status", Status.CANCELED));
	}

	public void takeSeat(Integer id) {
        IdQuery<Movie> idQuery = new IdQuery<Movie>(Movie.class, id);
        gigaSpace.change(idQuery, new ChangeSet().decrement("freeSeats", 1));
	}

	public void freeSeat(Integer id) {
        IdQuery<Movie> idQuery = new IdQuery<Movie>(Movie.class, id);
        gigaSpace.change(idQuery, new ChangeSet().increment("freeSeats", 1));
	}

	public Movie getMovie(Integer id) {
		return gigaSpace.readById(Movie.class, id);
	}

	public void updateMovie(Movie movie) {
		movie.setFreeSeats(movie.getTotalSeats());
		Movie template = new Movie();
		template.setIdMovie(movie.getIdMovie());
		gigaSpace.clear(template);
		gigaSpace.write(movie);
	}

    public void up(Map<String, Object> map, Integer id){
        IdQuery<Movie> idQuery = new IdQuery<Movie>(Movie.class, id);
        for(String s:map.keySet()){
        gigaSpace.change(idQuery, new ChangeSet().set(s, (Serializable)map.get(s)));
        }
    }
}
