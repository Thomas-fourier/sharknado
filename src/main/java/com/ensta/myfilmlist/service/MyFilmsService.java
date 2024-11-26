package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface MyFilmsService {
    /**
     * Met à jour le statut célèbre d'un réalisateur.
     * @param Real Rélalisateur à mettre à jour.
     * @return Retourne le réalisateur mis à jour.
     * @throws ServiceException En cas d'érreur.
     */
    public Realisateur updateRealisateurCelebre(Realisateur Real) throws ServiceException ;

    /**
     * Calcule la durée totale d'une liste de films
     * @param films Liste des films
     * @return La somme des durées des films
     */
    public int calculerDureeTotale(@NotNull List<Film> films);

    /**
     * Calcule la note moyenne sur un tableau.
     * @param notes Notes sous forme d'un tableau de double
     * @return Moyenne des notes arrondi à 2 chiffres après la virgule.
     */
    public double calculerNoteMoyenne(double[] notes);

    /**
     * Get all the films in database
     *
     * @return the list of all the films DTOs
     */
    public List<FilmDTO> findAllFilms() throws ServiceException;

    /**
     * Create a film from a film form
     * @param filmForm the form from which to create a film
     * @return the filmDTO created
     * @throws ServiceException if a connection error occurs or the realisateur does not exist
     */
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException;

    /**
     * Return all the realisateurs.
     * @return list of realisateurs
     * @throws ServiceException if a connection error occurs
     */
    List<RealisateurDTO> findAllRealisateurs() throws ServiceException;

    /**
     * Find a realisateur by its name and surname
     * @param nom relisateur's name
     * @param prenom realisateur's surname
     * @return the realisateur
     * @throws ServiceException if a connection error occurs
     */
    RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;

    /**
     * Find a film by its identifier
     * @param id identifier of the film
     * @return the film DTO
     * @throws ServiceException if a connection error occurs
     */
    FilmDTO findFilmById(long id) throws ServiceException;

    /**
     * Delete a film
     * @param id identifier of the film to delete
     * @throws ServiceException if a connection error occurs
     */
    void deleteFilm(long id) throws ServiceException;
}
