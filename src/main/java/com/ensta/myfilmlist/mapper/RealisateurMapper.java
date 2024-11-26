package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.model.Realisateur;

import java.util.List;
import java.util.stream.Collectors;

public class RealisateurMapper {
    public static List<RealisateurDTO> convertRealisateurToRealisateurDTOs(List<Realisateur> realisateur) {
        return realisateur.stream()
                .map(RealisateurMapper::convertRealisateurToRealisateurDTO)
                .collect(Collectors.toList());

    }

    public static List<Realisateur> convertRealisateurDTOToRealisateurs(List<RealisateurDTO> realisateur) {
        return realisateur.stream()
                .map(RealisateurMapper::convertRealisateurDTOToRealisateur)
                .collect(Collectors.toList());

    }

    public static RealisateurDTO convertRealisateurToRealisateurDTO(Realisateur realisateur) {
        RealisateurDTO realisateurDTO = new RealisateurDTO();
        realisateurDTO.setId(realisateur.getId());
        realisateurDTO.setNom(realisateur.getNom());
        realisateurDTO.setPrenom(realisateur.getPrenom());
        realisateurDTO.setDateNaissance(realisateur.getDateNaissance());
        realisateurDTO.setCelebre(realisateur.getCelebre());
        return realisateurDTO;
    }

    public static Realisateur convertRealisateurDTOToRealisateur(RealisateurDTO realisateurDTO) {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(realisateurDTO.getId());
        realisateur.setNom(realisateurDTO.getNom());
        realisateur.setPrenom(realisateurDTO.getPrenom());
        realisateur.setDateNaissance(realisateurDTO.getDateNaissance());
        realisateur.setCelebre(realisateurDTO.getCelebre());
        return realisateur;
    }
}