package com.ensta.myfilmlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class MyFilmsServiceTests {

    @InjectMocks
    private MyFilmsServiceImpl myFilmsService;

    @Mock
    private FilmDAO filmDAO;

    @Mock
    private RealisateurDAO realisateurDAO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateRealisateurCelebre_BecomesCelebre() throws ServiceException {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(5);
        realisateur.setNom("Christopher Nolan");

        List<Film> films = new ArrayList<>();
        films.add(new Film());
        films.add(new Film());
        films.add(new Film());
        realisateur.setFilmRealises(films);

        when(realisateurDAO.update(realisateur)).thenReturn(realisateur);

        Realisateur updatedRealisateur = myFilmsService.updateRealisateurCelebre(realisateur);

        assertNotNull(updatedRealisateur);
        assertTrue(updatedRealisateur.getCelebre(), "Le réalisateur doit être marqué comme célèbre.");
        verify(realisateurDAO, times(1)).update(realisateur);
    }

    @Test
    void testUpdateRealisateurCelebre_RemainsNonCelebre() throws ServiceException {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(1L);
        realisateur.setNom("Quentin Tarantino");

        List<Film> films = new ArrayList<>();
        films.add(new Film());
        films.add(new Film());
        realisateur.setFilmRealises(films);

        when(realisateurDAO.update(realisateur)).thenReturn(realisateur);
        Realisateur updatedRealisateur = myFilmsService.updateRealisateurCelebre(realisateur);

        assertNotNull(updatedRealisateur);
        assertFalse(updatedRealisateur.getCelebre(), "Le réalisateur ne doit pas être marqué comme célèbre.");
        verify(realisateurDAO, times(1)).update(realisateur);
    }

    @Test
    void testUpdateRealisateurCelebre_ThrowsServiceException() {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(1L);
        realisateur.setNom("Ridley Scott");

        doThrow(new RuntimeException("Database error")).when(realisateurDAO).update(realisateur);

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            myFilmsService.updateRealisateurCelebre(realisateur);
        });

        assertEquals("Une erreur est survenue lors du traitement de Real.", exception.getMessage());
        verify(realisateurDAO, times(1)).update(realisateur);
    }

    @Test
    void testCalculerDureeTotale_NormalCase() {
        Film film_1 = new Film();
        film_1.setTitre("Les deux tours");
        film_1.setDuree(120);

        Film film_2 = new Film();
        film_2.setTitre("La vivi mobile");
        film_2.setDuree(180);

        Film film_3 = new Film();
        film_3.setTitre("Les autres");
        film_3.setDuree(60);

        List<Film> films = List.of(film_1, film_2, film_3);

        int totalDuree = myFilmsService.calculerDureeTotale(films);

        assertEquals(360, totalDuree, "La durée totale doit être 360 minutes.");
    }

    @Test
    void testCalculerDureeTotale_EmptyList() {
        List<Film> films = new ArrayList<>();

        int totalDuree = myFilmsService.calculerDureeTotale(films);

        assertEquals(0, totalDuree, "La durée totale d'une liste vide doit être 0.");
    }

    @Test
    void testCalculerNoteMoyenne_NormalCase() {
        double[] notes = { 4.5, 3.0, 5.0, 4.0 };

        double moyenne = myFilmsService.calculerNoteMoyenne(notes);

        assertEquals(4.125, moyenne, 0.001, "La moyenne doit être correcte pour des notes normales.");
    }

    @Test
    void testCalculerNoteMoyenne_EmptyArray() {
        double[] notes = {};

        double moyenne = myFilmsService.calculerNoteMoyenne(notes);

        assertEquals(0.0, moyenne, "La moyenne d'un tableau vide doit être 0.0.");
    }

    @Test
    void testCalculerNoteMoyenne_OneNote() {
        double[] notes = { 3.5 };

        double moyenne = myFilmsService.calculerNoteMoyenne(notes);

        assertEquals(3.5, moyenne, "La moyenne doit être égale à la seule note.");
    }

    @Test
    void testFindAllFilms_Success() throws ServiceException {
        Film film_1 = new Film();
        film_1.setTitre("Les deux tours");
        film_1.setDuree(120);

        Film film_2 = new Film();
        film_2.setTitre("La vivi mobile");
        film_2.setDuree(180);

        Film film_3 = new Film();
        film_3.setTitre("Les autres");
        film_3.setDuree(60);

        List<Film> films = List.of(film_1, film_2, film_3);

        when(filmDAO.findAll()).thenReturn(films);
        List<FilmDTO> expectedDTOs = FilmMapper.convertFilmToFilmDTOs(films);

        List<FilmDTO> actualDTOs = myFilmsService.findAllFilms();

        assertNotNull(actualDTOs, "La liste retournée ne doit pas être nulle.");
        assertEquals(expectedDTOs.size(), actualDTOs.size(), "La taille des listes doit correspondre.");
        assertEquals(expectedDTOs.get(0).getTitre(), actualDTOs.get(0).getTitre(), "Les titres doivent correspondre.");
        verify(filmDAO, times(1)).findAll();
    }

    @Test
    void testFindAllFilms_EmptyList() throws ServiceException {
        when(filmDAO.findAll()).thenReturn(new ArrayList<>());

        List<FilmDTO> actualDTOs = myFilmsService.findAllFilms();

        assertNotNull(actualDTOs, "La liste retournée ne doit pas être nulle.");
        assertTrue(actualDTOs.isEmpty(), "La liste doit être vide.");
        verify(filmDAO, times(1)).findAll();
    }

    @Test
    void testFindAllFilms_ThrowsServiceException() {
        when(filmDAO.findAll()).thenThrow(new RuntimeException("Database error"));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            myFilmsService.findAllFilms();
        });

        assertEquals("Error while finding films", exception.getMessage());
        assertNotNull(exception.getCause(), "L'exception doit avoir une cause.");
        assertEquals("Database error", exception.getCause().getMessage(), "Le message de la cause doit correspondre.");
        verify(filmDAO, times(1)).findAll();
    }

    @Test
    void testCreateFilm_Success() throws ServiceException {

        FilmForm filmForm = new FilmForm();
        filmForm.setRealisateurId(5);
        filmForm.setTitre("Inception");

        Film film = new Film();
        film.setTitre("Inception");

        Realisateur realisateur = new Realisateur();
        realisateur.setId(5);
        realisateur.setPrenom("Christopher");
        realisateur.setNom("Nolan");

        Film savedFilm = new Film();
        savedFilm.setTitre("Inception");
        savedFilm.setRealisateur(realisateur);

        when(filmDAO.save(any(Film.class))).thenReturn(savedFilm);
        when(realisateurDAO.findById(5)).thenReturn(Optional.of(realisateur));

        FilmDTO filmDTO = myFilmsService.createFilm(filmForm);

        assertNotNull(filmDTO);
        assertEquals("Inception", filmDTO.getTitre());
        assertEquals("Christopher", filmDTO.getRealisateur().getPrenom());
        assertEquals("Nolan", filmDTO.getRealisateur().getNom());
        verify(realisateurDAO, times(1)).findById(5);
        verify(filmDAO, times(1)).save(any(Film.class));
    }

    @Test
    void testCreateFilm_RealisateurNotFound() {

        FilmForm filmForm = new FilmForm();
        filmForm.setRealisateurId(10);

        when(realisateurDAO.findById(10)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            myFilmsService.createFilm(filmForm);
        });

        assertEquals("Couldn't find realisateur", exception.getMessage());
        verify(realisateurDAO, times(1)).findById(10);
        verify(filmDAO, never()).save(any(Film.class));
    }

    @Test
    void testFindAllRealisateurs_Success() throws ServiceException {
        Realisateur realisateur_1 = new Realisateur();
        realisateur_1.setNom("Nolan");
        realisateur_1.setPrenom("Chirstopher");
        realisateur_1.setId(1);

        Realisateur realisateur_2 = new Realisateur();
        realisateur_2.setNom("Spielberg");
        realisateur_2.setPrenom("Steven");
        realisateur_2.setId(2);

        List<Realisateur> realisateurs = List.of(realisateur_1, realisateur_2);
        List<RealisateurDTO> expectedDTOs = RealisateurMapper.convertRealisateurToRealisateurDTOs(realisateurs);

        when(realisateurDAO.findAllRealisateur()).thenReturn(realisateurs);

        List<RealisateurDTO> actualDTOs = myFilmsService.findAllRealisateurs();

        assertNotNull(actualDTOs, "La liste retournée ne doit pas être nulle.");
        assertEquals(expectedDTOs.size(), actualDTOs.size(), "La taille des listes doit correspondre.");
        assertEquals(expectedDTOs.get(0).getNom(), actualDTOs.get(0).getNom(),
                "Les noms des réalisateurs doivent correspondre.");
        verify(realisateurDAO, times(1)).findAllRealisateur();
    }

    @Test
    void testFindAllRealisateurs_EmptyList() throws ServiceException {
        when(realisateurDAO.findAllRealisateur()).thenReturn(new ArrayList<>());

        List<RealisateurDTO> actualDTOs = myFilmsService.findAllRealisateurs();

        assertNotNull(actualDTOs, "La liste retournée ne doit pas être nulle.");
        assertTrue(actualDTOs.isEmpty(), "La liste doit être vide.");
        verify(realisateurDAO, times(1)).findAllRealisateur();
    }

    @Test
    void testFindRealisateurByNomAndPrenom_Success() throws ServiceException {
        // Arrange
        Realisateur realisateur = new Realisateur();
        realisateur.setNom("Nolan");
        realisateur.setPrenom("Chirstopher");
        realisateur.setId(1);

        RealisateurDTO expectedDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur);

        when(realisateurDAO.findByNomAndPrenom("Nolan", "Christopher")).thenReturn(realisateur);

        RealisateurDTO actualDTO = myFilmsService.findRealisateurByNomAndPrenom("Nolan", "Christopher");

        assertNotNull(actualDTO, "Le RealisateurDTO ne doit pas être nul.");
        assertEquals(expectedDTO.getNom(), actualDTO.getNom(), "Les noms doivent correspondre.");
        assertEquals(expectedDTO.getPrenom(), actualDTO.getPrenom(), "Les prénoms doivent correspondre.");
        verify(realisateurDAO, times(1)).findByNomAndPrenom("Nolan", "Christopher");
    }

    @Test
    void testFindRealisateurByNomAndPrenom_NotFound() throws ServiceException {
        String nom = "Kubrick";
        String prenom = "Stanley";

        when(realisateurDAO.findByNomAndPrenom(nom, prenom)).thenReturn(null);

        RealisateurDTO actualDTO = myFilmsService.findRealisateurByNomAndPrenom(nom, prenom);

        assertNull(actualDTO, "Le RealisateurDTO doit être nul si le réalisateur n'est pas trouvé.");
        verify(realisateurDAO, times(1)).findByNomAndPrenom(nom, prenom);
    }

    @Test
    void testFindFilmById_Success() throws ServiceException {
        long filmId = 1L;
        Film film = new Film();
        film.setId(filmId);
        film.setTitre("Inception");
        FilmDTO expectedDTO = FilmMapper.convertFilmToFilmDTO(film);

        when(filmDAO.findById(filmId)).thenReturn(Optional.of(film));

        FilmDTO actualDTO = myFilmsService.findFilmById(filmId);

        assertNotNull(actualDTO, "Le FilmDTO ne doit pas être nul.");
        assertEquals(expectedDTO.getTitre(), actualDTO.getTitre(), "Les titres doivent correspondre.");
        verify(filmDAO, times(1)).findById(filmId);
    }

    @Test
    void testFindFilmById_NotFound() {
        long filmId = 1L;

        when(filmDAO.findById(filmId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> myFilmsService.findFilmById(filmId));
        assertEquals("No film of that ID found.", exception.getMessage());
        verify(filmDAO, times(1)).findById(filmId);
    }

    @Test
    void testDeleteFilm_Success() throws ServiceException {
        long filmId = 1L;

        Realisateur realisateur = new Realisateur();
        realisateur.setId(1L);
        Film film = new Film();
        film.setId(filmId);
        film.setTitre("Inception");
        film.setRealisateur(realisateur);

        when(filmDAO.findById(filmId)).thenReturn(Optional.of(film));
        doNothing().when(filmDAO).delete(film);
        when(realisateurDAO.update(realisateur)).thenReturn(realisateur);

        myFilmsService.deleteFilm(filmId);

        verify(filmDAO, times(1)).findById(filmId);
        verify(filmDAO, times(1)).delete(film);
        verify(realisateurDAO, times(1)).update(realisateur);
        assertFalse(realisateur.getFilmRealises().contains(film),
                "Le film doit être supprimé de la liste des films réalisés par le réalisateur.");
    }

    @Test
    void testDeleteFilm_FilmNotFound() {
        long filmId = 1L;

        when(filmDAO.findById(filmId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> myFilmsService.deleteFilm(filmId));
        assertEquals("Film to delete was not found", exception.getMessage());
        verify(filmDAO, times(1)).findById(filmId);
        verify(filmDAO, never()).delete(any(Film.class));
    }
}
