package com.cinema.controller;


import com.cinema.errors.Errors;
import com.cinema.service.MovieService;
import com.gigaspaces.client.ChangeException;
import com.cinema.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public Movie getMovie(@PathVariable Integer id) {
        return movieService.getMovie(id);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMovie(@PathVariable Integer id, @RequestBody Movie movie) {
        movie.setIdMovie(id);
        movieService.updateMovie(movie);
    }

    @RequestMapping(value = "/up/{id}", method = RequestMethod.PATCH)
    @ResponseStatus((HttpStatus.NO_CONTENT))
    public void up(@PathVariable Integer id, @RequestBody Map<String, Object> map) {
        movieService.up(map, id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChangeException.class)
    public Errors changeEx() {
        Errors errors = new Errors();
        errors.setMessage("Bad data format");
        return errors;
    }

}
