package com.ensta.myfilmlist.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


// import static org.junit.jupiter.api.Assertions.*;

public class JdbcFilmDAO implements FilmDAO {

    private DataSource dataSource = ConnectionManager.getDataSource();
    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();

    private static class filmRowMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

            Film film = new Film();
            film.setTitre(rs.getString("titre"));
            film.setDuree(rs.getInt("duree"));
            film.setId(rs.getInt("realisateur_id"));

            return film;
        }
    }

    @Override
    public List<Film> findAll() {
        String query = "SELECT * FROM FILM";


        try {
            return jdbcTemplate.query(query, new filmRowMapper());
        } catch (Exception e) {
            // Log and handle exception (for simplicity, print stack trace)
            e.printStackTrace();

            return new ArrayList<>();
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
