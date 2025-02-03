package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.model.Realisateur;
import java.util.List;
import com.ensta.myfilmlist.form.RealisateurForm;
import java.util.stream.Collectors;

public class RealisateurMapper {

    /**
     * Converts a list of realisateurs into realisateur DTO
     * @param realisateur a list of realisateur
     * @return the corresponding list of realisateur DTO
     */
    public static List<RealisateurDTO> convertRealisateurToRealisateurDTOs(List<Realisateur> realisateur) {
        return realisateur.stream()
                .map(RealisateurMapper::convertRealisateurToRealisateurDTO)
                .collect(Collectors.toList());

    }

    /**
     * Converts a list of realisateurs DTO into realisateur
     * @param realisateur a list of realisateur DTO
     * @return the corresponding list of realisateur
     */
    public static List<Realisateur> convertRealisateurDTOToRealisateurs(List<RealisateurDTO> realisateur) {
        return realisateur.stream()
                .map(RealisateurMapper::convertRealisateurDTOToRealisateur)
                .collect(Collectors.toList());

    }

    /**
     * Converts a realisateur into a realisateur DTO
     * @param realisateur realisateur to convert
     * @return corresponding realisateur DTO
     */
    public static RealisateurDTO convertRealisateurToRealisateurDTO(Realisateur realisateur) {
        RealisateurDTO realisateurDTO = new RealisateurDTO();
        realisateurDTO.setId(realisateur.getId());
        realisateurDTO.setNom(realisateur.getNom());
        realisateurDTO.setPrenom(realisateur.getPrenom());
        realisateurDTO.setDateNaissance(realisateur.getDateNaissance());
        realisateurDTO.setCelebre(realisateur.getCelebre());
        return realisateurDTO;
    }

    /**
     * Converts a realisateur DTO into a realisateur
     * @param realisateurDTO realisateur DTO to convert
     * @return corresponding realisateur
     */
    public static Realisateur convertRealisateurDTOToRealisateur(RealisateurDTO realisateurDTO) {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(realisateurDTO.getId());
        realisateur.setNom(realisateurDTO.getNom());
        realisateur.setPrenom(realisateurDTO.getPrenom());
        realisateur.setDateNaissance(realisateurDTO.getDateNaissance());
        realisateur.setCelebre(realisateurDTO.getCelebre());
        return realisateur;
    }

    /**
	 * Convertit un Form en realisateur.
	 * 
	 * @param filmForm le Form a convertir
	 * @return Un Film construit a partir des donnes du Form.
	 */
	public static Realisateur convertRealisateurFormToRealisateur(RealisateurForm realisateurForm) {
		Realisateur realisateur = new Realisateur();
		realisateur.setNom(realisateurForm.getNom());
		realisateur.setPrenom(realisateurForm.getPrenom());
        realisateur.setDateNaissance(realisateurForm.getDateNaissance());

		return realisateur;
	}
}
