package com.ensta.myfilmlist.model;

import javax.persistence.*;

/**
 * Represente un Film.
 */
@Entity
@Table(name = "Film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "duree")
    private int duree;

    @ManyToOne
    @JoinColumn(name = "realisateur_id", nullable = false)
    private Realisateur realisateur;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Realisateur getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(Realisateur realisateur) {
        this.realisateur = realisateur;
    }
}
