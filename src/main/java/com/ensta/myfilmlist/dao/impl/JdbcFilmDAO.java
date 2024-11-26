package com.ensta.myfilmlist.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcFilmDAO implements FilmDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RealisateurDAO realisateurDAO;

    private class FilmRowMapper implements RowMapper<Film> {

        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

            Film film = new Film();
            film.setTitre(rs.getString("titre"));
            film.setDuree(rs.getInt("duree"));
            film.setId(rs.getInt("film.id"));

            film.setRealisateur(realisateurDAO.findById(rs.getInt("realisateur_id")).orElse(null));

            return film;
        }
    }

    static private class FilmNoRealisateurRowMapper implements RowMapper<Film> {

        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

            Film film = new Film();
            film.setTitre(rs.getString("titre"));
            film.setDuree(rs.getInt("duree"));
            film.setId(rs.getInt("film.id"));

            return film;
        }
    }

    @Override
    public List<Film> findAll() {
        String query = "SELECT * FROM FILM JOIN Realisateur ON film.realisateur_id = Realisateur.id";


        try {
            return jdbcTemplate.query(query, new FilmRowMapper());
        } catch (Exception e) {
            // Log and handle exception (for simplicity, print stack trace)
            System.out.println("Error while fetching films from database.\n" + e.getMessage());
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    @Override
    public Film save(Film film) {
        String query = "INSERT INTO Film(titre, duree, realisateur_id) VALUES(?, ?, ?);";

        jdbcTemplate.update(query, film.getTitre(), film.getDuree(), film.getRealisateur().getId());

        query = "SELECT id FROM FILM WHERE titre = ? AND duree = ? and realisateur_id = ?";
        film.setId(jdbcTemplate.queryForObject(query, Long.class, film.getTitre(), film.getDuree(), film.getRealisateur().getId()));

        return film;
    }

    @Override
    public Optional<Film> findById(long id) {
        String query = "SELECT * FROM Film JOIN Realisateur ON Film.realisateur_id = Realisateur.id WHERE Film.id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new JdbcFilmDAO.FilmRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Film film) {
        if (film != null) {
            jdbcTemplate.update("Delete FROM Film WHERE id = ?", film.getId());
        }
    }

    @Override
    public List<Film> findByRealisateurId(long realisateurId) {
        String query =
                "SELECT * FROM Film JOIN Realisateur ON Film.realisateur_id = Realisateur.id  WHERE realisateur_id = ?";

        try {
            return jdbcTemplate.query(query, new JdbcFilmDAO.FilmNoRealisateurRowMapper(), realisateurId);
        } catch (Exception e) {
            return List.of();
        }

    }
}




// class JdbcFilmDAOTest {

//     // private  jdbcFilmDAO;

//     // @BeforeEach

//         // Crée une instance du DAO
//     private JdbcFilmDAO jdbcFilmDAO = new JdbcFilmDAO();

//     @Test
//     void testFindAll() {
//         // Appelle la méthode findAll()
//         List<Film> films = jdbcFilmDAO.findAll();

//         // assertEquals(2, films.get(0).getTitre());
//         films.get(0).getTitre();
//         System.out.println(films.get(0).getTitre());
//         System.out.println(films.get(-1).getTitre());

//     }
// }
