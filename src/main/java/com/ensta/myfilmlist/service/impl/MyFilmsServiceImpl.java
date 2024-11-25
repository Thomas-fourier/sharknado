package com.ensta.myfilmlist.service.impl;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.exception.ServiceException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
     * @param films
     * @return Retourne la durée totale de la liste de films
     */
    public int calculerDureeTotale(List<Film> films) {
        return films.stream()
                .mapToInt(Film::getDuree)
                .sum();
    }

}
