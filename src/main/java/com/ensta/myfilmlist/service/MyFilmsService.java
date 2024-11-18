package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Realisateur;


public interface MyFilmsService {
    /**
     * Met à jour le statut célèbre d'un réalisateur.
     * @param Real Rélalisateur à mettre à jour.
     * @return Retourne le réalisateur mis à jour.
     * @throws ServiceException En cas d'érreur.
     */
    public Realisateur updateRealisateurCelebre(Realisateur Real) throws ServiceException ;
}
