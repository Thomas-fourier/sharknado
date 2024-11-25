package com.ensta.myfilmlist.dao;
import java.util.List;

import com.ensta.myfilmlist.model.Film;

public interface FilmDAO {

    /**
     * Find all films in database
     * @return list of films
     */
    List<Film> findAll();

    /**
     * Save a film to database
     * @param film film to save
     * @return the film with its ID in database
     */
    Film save(Film film);
}