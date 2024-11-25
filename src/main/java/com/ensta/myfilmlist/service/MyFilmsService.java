package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.exception.ServiceException;
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
}
