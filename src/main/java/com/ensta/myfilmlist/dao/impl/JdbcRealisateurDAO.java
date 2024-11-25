package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcRealisateurDAO implements RealisateurDAO {


    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();

    private static class RealisateurRowMapper implements RowMapper<Realisateur> {
        @Override
        public Realisateur mapRow(ResultSet rs, int rowNum) throws SQLException {

            Realisateur re = new Realisateur();
            re.setNom(rs.getString("nom"));
            re.setPrenom(rs.getString("prenom"));
            re.setCelebre(rs.getBoolean("celebre"));
            re.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            re.setId(rs.getInt("id"));

            return re;
        }
    }

    @Override
    public List<Realisateur> findAllRealisateur() {
        String query = "SELECT * FROM Realisateur";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            return jdbcTemplate.query(query, new RealisateurRowMapper());
        } catch (Exception e) {
            // Log and handle exception (for simplicity, print stack trace)
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) throws EmptyResultDataAccessException {
        String query = "SELECT * FROM Realisateur WHERE nom = ? AND prenom = ?";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            return jdbcTemplate.queryForObject(query, new RealisateurRowMapper(), nom, prenom);
        } catch (Exception e) {
            throw  new EmptyResultDataAccessException("Realisateur non trouvé ou trouvé plusieurs fois.",1,e);
        }
    }

    @Override
    public Optional<Realisateur> findById(long id) {
        String query = "SELECT * FROM Realisateur WHERE id = ?";

        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new RealisateurRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
