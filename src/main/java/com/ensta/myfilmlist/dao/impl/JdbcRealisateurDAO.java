package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Realisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRealisateurDAO implements RealisateurDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FilmDAO filmDAO;

    @Service
    private class RealisateurRowMapper implements RowMapper<Realisateur> {

        @Override
        public Realisateur mapRow(ResultSet rs, int rowNum) throws SQLException {

            Realisateur re = new Realisateur();
            re.setNom(rs.getString("nom"));
            re.setPrenom(rs.getString("prenom"));
            re.setCelebre(rs.getBoolean("celebre"));
            re.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            re.setId(rs.getInt("id"));

            re.setFilmRealises(filmDAO.findByRealisateurId(re.getId()));


            return re;
        }
    }

    @Override
    public List<Realisateur> findAllRealisateur() {
        String query = "SELECT * FROM Realisateur";

        try {
            return jdbcTemplate.query(query, new RealisateurRowMapper());
        } catch (Exception e) {
            // Log and handle exception (for simplicity, print stack trace)
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    @Override
    public Realisateur
    findByNomAndPrenom(String nom, String prenom) throws EmptyResultDataAccessException {
        String query = "SELECT * FROM Realisateur WHERE nom = ? AND prenom = ?";

        try {
            return jdbcTemplate.queryForObject(query, new RealisateurRowMapper(), nom, prenom);
        } catch (Exception e) {
            throw  new EmptyResultDataAccessException("Realisateur non trouvé ou trouvé plusieurs fois.",1,e);
        }
    }

    @Override
    public Optional<Realisateur> findById(long id) {
        String query = "SELECT * FROM Realisateur WHERE id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new RealisateurRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Realisateur update(Realisateur realisateur) {
        try {
            jdbcTemplate.update(
                    "UPDATE realisateur SET NOM = ?, PRENOM = ?, DATE_NAISSANCE = ?, CELEBRE = ? WHERE id = ?",
                    realisateur.getNom(), realisateur.getPrenom(), realisateur.getDateNaissance(),
                    realisateur.getCelebre(), realisateur.getId());
            return realisateur;
        } catch (Exception e) {
            throw e;
        }
    }
}
