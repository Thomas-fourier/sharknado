package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Realisateur;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Primary
public class JpaRealisateurDAO implements RealisateurDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Realisateur> findAllRealisateur() {
        TypedQuery<Realisateur> query = entityManager.createQuery("SELECT r FROM Realisateur r", Realisateur.class);
        return query.getResultList();
    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) throws EmptyResultDataAccessException {
        TypedQuery<Realisateur> query = entityManager.createQuery(
                "SELECT r FROM Realisateur r WHERE r.nom = :nom AND r.prenom = :prenom", Realisateur.class);
        query.setParameter("nom", nom);
        query.setParameter("prenom", prenom);
        Realisateur realisateur = query.getResultList().stream().findFirst().orElse(null);
        if (realisateur == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return realisateur;
    }

    @Override
    public Optional<Realisateur> findById(long id) {
        Realisateur realisateur = entityManager.find(Realisateur.class, id);
        return Optional.ofNullable(realisateur);
    }

    @Override
    public Realisateur update(Realisateur realisateur) {
        return entityManager.merge(realisateur);
    }
}
