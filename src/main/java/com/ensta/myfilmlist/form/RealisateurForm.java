package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

import org.springframework.lang.NonNull;

/**
 * Contient les donnees pour requeter un realisateur.
 */
public class RealisateurForm {

    @NonNull
    @NotBlank
    private String nom = "";

    @NonNull
    @NotBlank
    private String prenom = "";

    @Past
    private LocalDate dateNaissance;

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
}
