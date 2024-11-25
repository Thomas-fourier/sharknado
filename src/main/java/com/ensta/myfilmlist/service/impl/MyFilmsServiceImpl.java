package com.ensta.myfilmlist.service.impl;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.exception.ServiceException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MyFilmsServiceImpl implements MyFilmsService {
    public static final int NB_FILMS_MIN_REALISATEUR_CELEBRE = 3;

    @Override
    public Realisateur updateRealisateurCelebre(Realisateur Real) throws ServiceException {
        try {
            if (Real.getFilmRealises().size() >= NB_FILMS_MIN_REALISATEUR_CELEBRE) {
                Real.setCelebre(true);
            } else {
                Real.setCelebre(false);
            }
            return Real;
        } catch (Exception e) {
            throw new ServiceException("Une erreur est survenue lors du traitement de Real.", e);
        }
    }

    /**
     * Calcul la durée total de la liste de films
     * @param films liste des films
     * @return Retourne la durée totale de la liste de films
     */
    public int calculerDureeTotale(List<Film> films) {
        return films.stream()
                .mapToInt(Film::getDuree)
                .sum();
    }

    @Override
    public double calculerNoteMoyenne(double[] notes) {
        return Arrays.stream(notes).average().orElse(0.0);
    }

    @Override
    public List<FilmDTO> findAllFilms() throws ServiceException {
        try {
            FilmDAO bdd_access = new JdbcFilmDAO();

            return FilmMapper.convertFilmToFilmDTOs(bdd_access.findAll());
        } catch (Exception e) {
            throw new ServiceException("Error while finding films", e);
        }
    }

    @Override
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        // Convert form to film
        Film film = FilmMapper.convertFilmFormToFilm(filmForm);

        RealisateurDAO realisateurDAO = new JdbcRealisateurDAO();
        Optional<Realisateur> re = realisateurDAO.findById(filmForm.getRealisateurId());

        if (re.isPresent()) {
            film.setRealisateur(re.get());
            FilmDAO filmDAO = new JdbcFilmDAO();

            return FilmMapper.convertFilmToFilmDTO(filmDAO.save(film));
        }

        throw new ServiceException("Couldn't find realisateur");

    }

    @Override
    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException {
        RealisateurDAO realisateurDAO = new JdbcRealisateurDAO();
        return RealisateurMapper.convertRealisateurToRealisateurDTOs(realisateurDAO.findAllRealisateur());
    }

    @Override
    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException {
        RealisateurDAO realisateurDAO = new JdbcRealisateurDAO();
        try {
            return RealisateurMapper.convertRealisateurToRealisateurDTO(realisateurDAO.findByNomAndPrenom(nom, prenom));
        } catch (Exception e) {
            return null;
        }
    }


}
