package com.ensta.myfilmlist.service.impl;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class MyFilmsServiceImpl implements MyFilmsService {
    public static final int NB_FILMS_MIN_REALISATEUR_CELEBRE = 3;

    @Autowired
    private RealisateurDAO realisateurDAO;
    @Autowired
    private FilmDAO filmDAO;

    @Override
    public Realisateur updateRealisateurCelebre(Realisateur Real) throws ServiceException {
        try {
            Real.setCelebre(Real.getFilmRealises().size() >= NB_FILMS_MIN_REALISATEUR_CELEBRE);
            realisateurDAO.update(Real);
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
            return FilmMapper.convertFilmToFilmDTOs(filmDAO.findAll());
        } catch (Exception e) {
            throw new ServiceException("Error while finding films", e);
        }
    }

    @Override
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        // Convert form to film
        Film film = FilmMapper.convertFilmFormToFilm(filmForm);

        Optional<Realisateur> re = realisateurDAO.findById(filmForm.getRealisateurId());

        if (re.isPresent()) {
            film.setRealisateur(re.get());
            re.get().addFilmRealises(film);
            Film ret = filmDAO.save(film);
            ret.setRealisateur(updateRealisateurCelebre(re.get()));
            return FilmMapper.convertFilmToFilmDTO(ret);
        }

        throw new ServiceException("Couldn't find realisateur");

    }

    @Override
    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException {
        return RealisateurMapper.convertRealisateurToRealisateurDTOs(realisateurDAO.findAllRealisateur());
    }

    @Override
    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException {
        try {
            return RealisateurMapper.convertRealisateurToRealisateurDTO(realisateurDAO.findByNomAndPrenom(nom, prenom));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FilmDTO findFilmById(long id) throws ServiceException {
        Optional<Film> film = filmDAO.findById(id);
        try {
            return film.map(FilmMapper::convertFilmToFilmDTO).get();
        } catch (Exception e) {
            throw new ServiceException("No film of that ID found.");
        }
    }

    @Override
    public void deleteFilm(long id) throws ServiceException {
        Optional<Film> film = filmDAO.findById(id);

        if (film.isPresent()) {
            filmDAO.delete(film.get());
            film.get().getRealisateur().removeFilmRealises(film.get());
            updateRealisateurCelebre(film.get().getRealisateur());
            return;
        }

        throw new ServiceException("Film to delete was not found");
    }


}
