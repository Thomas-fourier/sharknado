package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import com.ensta.myfilmlist.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyFilmsServiceTests {

    private MyFilmsServiceImpl myFilmsService;

    @BeforeEach
    void setUp() {
        myFilmsService = new MyFilmsServiceImpl();
    }

    @Test
    void testCalculerDureeTotale_Rapide() {
        Film film_1 = new Film();
		film_1.setTitre("Les deux tours");
		film_1.setDuree(120);

        Film film_2 = new Film();
		film_1.setTitre("La vivi mobile");
		film_1.setDuree(180);

        Film film_3 = new Film();
		film_1.setTitre("La vivi mobile");
		film_1.setDuree(100);

        List<Film> films = List.of(film_1,film_2,film_3);
        // Act
        int dureeTotale = myFilmsService.calculerDureeTotale(films);

        // Assert
        assertEquals(300, dureeTotale, "La durée totale des films doit être correcte.");
    }

    @Test
    void testCalculerNoteMoyenne_Rapide() {
        // Arrange
        double[] notes = {4.0, 3.0, 5.0};

        // Act
        double moyenne = myFilmsService.calculerNoteMoyenne(notes);

        // Assert
        assertEquals(4.0, moyenne, "La moyenne des notes doit être correcte.");
    }

    @Test
    void testFindFilmById_Moyen() throws ServiceException {
        // Arrange
        Film film = new Film();
        film.setId(1L);
        film.setTitre("Un film");
        film.setDuree(120);
        film.setRealisateur(new Realisateur());

        // Inject data (simulation)
        // FilmDAO filmDAO = new FilmDAO();
        // filmDAO.save(film);
        // myFilmsService.setFilmDAO(filmDAO);

        // Act
        FilmDTO result = myFilmsService.findFilmById(1L);

        // Assert
        assertNotNull(result, "Le film ne doit pas être nul.");
        assertEquals("Un film", result.getTitre(), "Le titre doit correspondre.");
    }

    @Test
    void testUpdateRealisateurCelebre_Difficile() throws ServiceException {
        // Arrange
        Realisateur realisateur = new Realisateur();
        realisateur.setFilmRealises(Arrays.asList(new Film(), new Film(), new Film()));

        // Act
        Realisateur result = myFilmsService.updateRealisateurCelebre(realisateur);

        // Assert
        // assertTrue(result.isCelebre(), "Le réalisateur doit être marqué comme célèbre.");
    }
}
