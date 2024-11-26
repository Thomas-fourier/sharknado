package com.ensta.myfilmlist.dao;
import java.util.List;
import java.util.Optional;

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

    /**
     * Search a film by its identifier.
     * @param id the identifier
     * @return an optional Film
     */
    Optional<Film> findById(long id);

    /**
     * Delete a film.
     * @param film the film to delete
     */
    void delete(Film film);

    /**
     * Find all the movies made by a moviemaker.
     * @param realisateurId identifier of the realisateur
     * @return a list of films made by the realisateur
     */
    List<Film> findByRealisateurId(long realisateurId);
}