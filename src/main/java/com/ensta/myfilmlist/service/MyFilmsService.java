package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Realisateur;


public interface MyFilmsService {
    public Realisateur updateRealisateurCelebre(Realisateur Real) throws ServiceException ;
}
