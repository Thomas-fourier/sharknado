package com.ensta.myfilmlist.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Realisateur")
public class Realisateur {
    @Transient
    private final int filmRealisesCelebrite = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @OneToMany(mappedBy = "realisateur", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Film> filmRealises = new ArrayList<>();

    @Column(name = "celebre")
    private boolean celebre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public List<Film> getFilmRealises() {
        return filmRealises;
    }

    public void addFilmRealises(Film film) {
        filmRealises.add(film);
        if (celebre || filmRealises.size() >= filmRealisesCelebrite) {
            celebre = true;
        }
    }

    public void removeFilmRealises(Film film) {
        filmRealises.remove(film);
        if (!celebre || filmRealises.size() < filmRealisesCelebrite) {
            celebre = false;
        }
    }

    public void setFilmRealises(List<Film> filmRealises) {
        this.filmRealises = filmRealises;
        celebre = (filmRealises.size() >= filmRealisesCelebrite);
    }

    public boolean getCelebre() {
        return celebre;
    }

    public void setCelebre(boolean celebre) {
        this.celebre = celebre;
    }
}
