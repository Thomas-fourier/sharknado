package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.Realisateur;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public interface RealisateurDAO {
    List<Realisateur> findAllRealisateur();

    Realisateur findByNomAndPrenom(String nom, String prenom) throws EmptyResultDataAccessException;

    Optional<Realisateur> findById(long id);

    Realisateur update(Realisateur realisateur);

}