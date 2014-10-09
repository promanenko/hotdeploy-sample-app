package com.cinema.dao;

import com.cinema.entity.Movie;

import java.util.Map;

public interface MovieDao {
	public void addMovie(Movie movie);
	public void deleteMovie(Integer id);
	public void takeSeat(Integer id);
	public void freeSeat(Integer id);
	public Movie getMovie(Integer id);
	public void updateMovie(Movie movie);
    public void up(Map<String, Object> map, Integer id);

}
