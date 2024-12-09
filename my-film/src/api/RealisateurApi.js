import axios from 'axios';

const REALISATEUR_URI = 'http://localhost:8080/realisateur'; // URL de l'API des réalisateurs

/**
 * Récupère tous les réalisateurs
 */
export function getAllRealisateurs() {
    return axios.get(REALISATEUR_URI);
}

/**
 * Ajoute un nouveau réalisateur
 * @param {Object} realisateur - Les données du réalisateur à ajouter
 */
export function postRealisateur(realisateur) {
    return axios.post(REALISATEUR_URI, realisateur);
}

/**
 * Met à jour un réalisateur existant
 * @param {string} realisateurId - L'ID du réalisateur à mettre à jour
 * @param {Object} realisateur - Les nouvelles données du réalisateur
 */
export function putRealisateur(realisateurId, realisateur) {
    return axios.put(`${REALISATEUR_URI}/${realisateurId}`, realisateur);
}

/**
 * Supprime un réalisateur
 * @param {string} realisateurId - L'ID du réalisateur à supprimer
 */
export function deleteRealisateur(realisateurId) {
    return axios.delete(`${REALISATEUR_URI}/${realisateurId}`);
}
