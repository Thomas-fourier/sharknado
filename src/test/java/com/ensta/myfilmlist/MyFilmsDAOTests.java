package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class MyFilmsDAOTests {

    @Autowired
    private JdbcFilmDAO filmDAO;

    @Autowired
    private JdbcRealisateurDAO realisateurDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        // Supprimer les données existantes pour éviter les conflits
        jdbcTemplate.execute("DELETE FROM Film");
        jdbcTemplate.execute("DELETE FROM Realisateur");

        // Réinsérer les données de test
        jdbcTemplate.execute("INSERT INTO Realisateur (id, nom, prenom, celebre, date_naissance) " +
                "VALUES (1, 'Spielberg', 'Steven', true, '1946-12-18')");
        jdbcTemplate.execute("INSERT INTO Film (id, titre, duree, realisateur_id) " +
                "VALUES (1, 'Jurassic Park', 127, 1)");
    }

    // Tests pour JdbcFilmDAO
    @Test
    void testFindAllFilms_Rapide() {
        List<Film> films = filmDAO.findAll();
        assertNotNull(films, "La liste des films ne doit pas être null");
        assertEquals(1, films.size(), "Il doit y avoir exactement 1 film dans la base");
        assertEquals("Jurassic Park", films.get(0).getTitre(), "Le titre du film doit être 'Jurassic Park'");
    }

    @Test
    void testFindFilmById_Rapide() {
        Optional<Film> film = filmDAO.findById(1);
        assertTrue(film.isPresent(), "Le film avec l'ID 1 doit exister");
        assertEquals("Jurassic Park", film.get().getTitre(), "Le titre du film doit être 'Jurassic Park'");
    }

    @Test
    void testSaveFilm_Rapide() {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(1);

        Film newFilm = new Film();
        newFilm.setTitre("E.T.");
        newFilm.setDuree(115);
        newFilm.setRealisateur(realisateur);

        Film savedFilm = filmDAO.save(newFilm);
        assertNotNull(savedFilm.getId(), "L'ID du film sauvegardé ne doit pas être null");
        assertEquals("E.T.", savedFilm.getTitre(), "Le titre du film sauvegardé doit être 'E.T.'");
    }

    // Tests pour JdbcRealisateurDAO reussi
    @Test
    void testFindAllRealisateurs_reussi() {
        List<Realisateur> realisateurs = realisateurDAO.findAllRealisateur();
        assertNotNull(realisateurs, "La liste des réalisateurs ne doit pas être null");
        assertEquals(1, realisateurs.size(), "Il doit y avoir exactement 1 réalisateur dans la base");
        assertEquals("Spielberg", realisateurs.get(0).getNom(), "Le nom du réalisateur doit être 'Spielberg'");
    }

    @Test
    void testFindRealisateurById_Rapide() {
        Optional<Realisateur> realisateur = realisateurDAO.findById(1);
        assertTrue(realisateur.isPresent(), "Le réalisateur avec l'ID 1 doit exister");
        assertEquals("Spielberg", realisateur.get().getNom(), "Le nom du réalisateur doit être 'Spielberg'");
    }

    @Test
    void testFindRealisateurByNomAndPrenom_Rapide() {
        Realisateur realisateur = realisateurDAO.findByNomAndPrenom("Spielberg", "Steven");
        assertNotNull(realisateur, "Le réalisateur 'Steven Spielberg' doit exister");
        assertEquals("Steven", realisateur.getPrenom(), "Le prénom du réalisateur doit être 'Steven'");
    }

    @Test
    void testUpdateRealisateur_Rapide() {
        Optional<Realisateur> optionalRealisateur = realisateurDAO.findById(1);
        assertTrue(optionalRealisateur.isPresent(), "Le réalisateur avec l'ID 1 doit exister");

        Realisateur realisateur = optionalRealisateur.get();
        realisateur.setNom("Spielberg Jr.");
        realisateurDAO.update(realisateur);

        Realisateur updatedRealisateur = realisateurDAO.findById(1).get();
        assertEquals("Spielberg Jr.", updatedRealisateur.getNom(), "Le nom du réalisateur doit être mis à jour");
    }

}
