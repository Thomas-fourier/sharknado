package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.Realisateur;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public interface RealisateurDAO {
    /**
     * Find all the realisateurs in bdd.
     * @return list of realisateurs
     */
    List<Realisateur> findAllRealisateur();

    /**
     * Find a realisateur by its name and surname
     * @param nom name of the realisateur
     * @param prenom surname of the ralisateur
     * @return the realisateur
     * @throws EmptyResultDataAccessException if the realisateur is not found
     */
    Realisateur findByNomAndPrenom(String nom, String prenom) throws EmptyResultDataAccessException;

    /**
     * Find a realisateur by its id.
     * @param id identifier of the realisateur.
     * @return an optional realisateur
     */
    Optional<Realisateur> findById(long id);

    /**
     * Update the realisateur's value.
     * @param realisateur The realisateur to be put in DB.
     * @return the Realisateur updated.
     */
    Realisateur update(Realisateur realisateur);

    /**
     * Save a new realisateur in the database.
     * @param realisateur The realisateur to be saved.
     * @return the saved Realisateur.
     */
    Realisateur save(Realisateur realisateur);
}
