// import javax.sql.DataSource;
// import com.ensta.myfilmlist.persistence.ConnectionManager;




// public class JdbcFilmDAO{
//     private DataSource dataSource = ConnectionManager.getDataSource();

// }

package com.ensta.myfilmlist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.ConnectionManager;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;


// import static org.junit.jupiter.api.Assertions.*;

public class JdbcFilmDAO implements FilmDAO {

    private DataSource dataSource = ConnectionManager.getDataSource();

    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String query = "SELECT * FROM FILM";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Film film = new Film();
                film.setTitre(resultSet.getString("titre"));
                film.setDuree(resultSet.getInt("duree"));
                film.setId(resultSet.getInt("realisateur_id"));
                films.add(film);
            }
        } catch (Exception e) {
            // Log and handle exception (for simplicity, print stack trace)
            e.printStackTrace();
        }
        return films;
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
