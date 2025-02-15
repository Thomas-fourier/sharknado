package com.ensta.myfilmlist;

import com.ensta.myfilmlist.controller.impl.FilmResourceImpl;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import com.ensta.myfilmlist.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;

import com.ensta.myfilmlist.exception.ControllerException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MyFilmsControllerTests {
    @InjectMocks
    private FilmResourceImpl filmResourceImpl;

    @Mock
    private MyFilmsServiceImpl myFilmsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFilms_Success() throws Exception {
        // Arrange
        FilmDTO film1 = new FilmDTO();
        film1.setTitre("Film 1");
        film1.setDuree(120);

        FilmDTO film2 = new FilmDTO();
        film2.setTitre("Film 2");
        film2.setDuree(90);

        List<FilmDTO> mockFilmList = Arrays.asList(film1, film2);

        // Simuler le comportement de findAllFilms() du service
        when(myFilmsService.findAllFilms()).thenReturn(mockFilmList);

        // Act
        ResponseEntity<List<FilmDTO>> response = filmResourceImpl.getAllFilms();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Vérifie que le statut HTTP est 200 (OK)
        List<FilmDTO> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size()); // Vérifie que deux films sont retournés
        assertEquals("Film 1", body.get(0).getTitre());
        assertEquals("Film 2", body.get(1).getTitre());

        verify(myFilmsService, times(1)).findAllFilms(); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testGetAllFilms_Failure() throws Exception {
        // Arrange
        when(myFilmsService.findAllFilms()).thenThrow(new ServiceException("Error retrieving films"));

        // Act & Assert
        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmResourceImpl.getAllFilms();
        });

        assertNotNull(exception);
        assertEquals("Error retrieving films", exception.getMessage()); // Vérifie le message d'erreur
        verify(myFilmsService, times(1)).findAllFilms(); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testGetFilmById_Success() throws Exception {
        // Arrange
        long filmId = 1L;

        FilmDTO mockFilmDTO = new FilmDTO();
        mockFilmDTO.setId(filmId);
        mockFilmDTO.setTitre("Inception");

        // Simuler le comportement du service
        when(myFilmsService.findFilmById(filmId)).thenReturn(mockFilmDTO);

        // Act
        ResponseEntity<FilmDTO> response = filmResourceImpl.getFilmById(filmId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Vérifie que le statut HTTP est 200 (OK)
        FilmDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("Inception", body.getTitre()); // Vérifie le titre du film
        assertEquals(filmId, body.getId()); // Vérifie l'ID du film

        verify(myFilmsService, times(1)).findFilmById(filmId); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testGetFilmById_NotFound() throws Exception {
        // Arrange
        long filmId = 999L;

        // Simuler une exception levée par le service
        when(myFilmsService.findFilmById(filmId)).thenThrow(new ServiceException("Film not found"));

        // Act & Assert
        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmResourceImpl.getFilmById(filmId);
        });

        assertNotNull(exception);
        assertEquals("Film not found", exception.getMessage()); // Vérifie le message de l'exception
        verify(myFilmsService, times(1)).findFilmById(filmId); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testCreateFilm_Success() throws Exception {
        // Arrange
        FilmForm filmForm = new FilmForm();
        filmForm.setTitre("Inception");
        filmForm.setDuree(148);
        filmForm.setRealisateurId(1);

        FilmDTO mockFilmDTO = new FilmDTO();
        mockFilmDTO.setId(1L);
        mockFilmDTO.setTitre("Inception");
        mockFilmDTO.setDuree(148);

        // Simuler le comportement du service
        when(myFilmsService.createFilm(filmForm)).thenReturn(mockFilmDTO);

        // Act
        ResponseEntity<FilmDTO> response = filmResourceImpl.createFilm(filmForm);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Vérifie que le statut HTTP est 200 (OK)
        FilmDTO body = response.getBody();
        assertNotNull(body);
        assertEquals("Inception", body.getTitre()); // Vérifie le titre du film
        assertEquals(148, body.getDuree()); // Vérifie la durée du film

        verify(myFilmsService, times(1)).createFilm(filmForm); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testCreateFilm_Failure() throws Exception {
        // Arrange
        FilmForm filmForm = new FilmForm();
        filmForm.setTitre("Inception");
        filmForm.setDuree(148);
        filmForm.setRealisateurId(1);

        // Simuler une exception levée par le service
        when(myFilmsService.createFilm(filmForm)).thenThrow(new ServiceException("Error creating film"));

        // Act & Assert
        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmResourceImpl.createFilm(filmForm);
        });

        assertNotNull(exception);
        assertEquals("Error creating film", exception.getMessage()); // Vérifie le message de l'exception
        verify(myFilmsService, times(1)).createFilm(filmForm); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testDeleteFilm_Success() throws Exception {
        // Arrange
        long filmId = 1L;

        // Simuler un comportement de succès pour le service
        doNothing().when(myFilmsService).deleteFilm(filmId);

        // Act
        ResponseEntity<Boolean> response = filmResourceImpl.deleteFilm(filmId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Vérifie que le statut HTTP est OK
        Boolean body = response.getBody();
        assertNotNull(body);
        assertTrue(body); // Vérifie que la réponse contient `true`
        verify(myFilmsService, times(1)).deleteFilm(filmId); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testDeleteFilm_Failure() throws Exception {
        // Arrange
        long filmId = 1L;

        // Simuler une exception levée par le service
        doThrow(new ServiceException("Error deleting film")).when(myFilmsService).deleteFilm(filmId);

        // Act & Assert
        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmResourceImpl.deleteFilm(filmId);
        });

        assertNotNull(exception);
        assertEquals("Error deleting film", exception.getMessage()); // Vérifie le message de l'exception

        verify(myFilmsService, times(1)).deleteFilm(filmId); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testGetAllRealisateurs_Success() throws Exception {
        // Arrange
        RealisateurDTO real1 = new RealisateurDTO();
        real1.setNom("Spielberg");
        real1.setPrenom("Steven");

        RealisateurDTO real2 = new RealisateurDTO();
        real2.setNom("Nolan");
        real2.setPrenom("Christopher");

        List<RealisateurDTO> mockRealisateurList = Arrays.asList(real1, real2);

        // Simuler le comportement de findAllRealisateurs() du service
        when(myFilmsService.findAllRealisateurs()).thenReturn(mockRealisateurList);

        ResponseEntity<List<RealisateurDTO>> response = filmResourceImpl.getAllRealisateurs();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // Vérifie que le statut HTTP est 200 (OK)
        List<RealisateurDTO> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size()); // Vérifie que deux réalisateurs sont retournés
        assertEquals("Spielberg", body.get(0).getNom());
        assertEquals("Nolan", body.get(1).getNom());

        verify(myFilmsService, times(1)).findAllRealisateurs(); // Vérifie que le service a été appelé une fois
    }

    @Test
    void testGetAllRealisateurs_Failure() throws Exception {
        // Arrange
        when(myFilmsService.findAllRealisateurs()).thenThrow(new ServiceException("Error retrieving realisateurs"));

        // Act & Assert
        ControllerException exception = assertThrows(ControllerException.class, () -> {
            filmResourceImpl.getAllRealisateurs();
        });

        assertNotNull(exception);
        assertEquals("Error retrieving realisateurs", exception.getMessage()); // Vérifie le message d'erreur
        verify(myFilmsService, times(1)).findAllRealisateurs(); // Vérifie que le service a été appelé une fois
    }
}

