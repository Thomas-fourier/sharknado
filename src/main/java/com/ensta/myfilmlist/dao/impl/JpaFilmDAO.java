package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.model.Film;

import org.springframework.context.annotation.Primary;
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
public class JpaFilmDAO implements FilmDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Film> findAll() {
        TypedQuery<Film> query = entityManager.createQuery(
            "SELECT f FROM Film f JOIN FETCH f.realisateur", 
            Film.class
        );
        return query.getResultList();
    }    

    @Override
    public Film save(Film film) {
        if (film.getId() == 0) {
            entityManager.persist(film);
        } else {
            film = entityManager.merge(film);
        }
        return film;
    }

    @Override
    public Optional<Film> findById(long id) {
        Film film = entityManager.find(Film.class, id);
        return Optional.ofNullable(film);
    }

    @Override
    public void delete(Film film) {
        if (entityManager.find(Film.class, film.getId()) != null) {
            if (!entityManager.contains(film)) {
                film = entityManager.merge(film);
            }
            entityManager.remove(film);
        }
    }

    @Override
    public List<Film> findByRealisateurId(long realisateurId) {
        TypedQuery<Film> query = entityManager.createQuery("SELECT f FROM Film f WHERE f.realisateur.id = :realisateurId", Film.class);
        query.setParameter("realisateurId", realisateurId);
        return query.getResultList();
    }
}
