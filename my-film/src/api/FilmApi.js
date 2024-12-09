import axios from 'axios';

const FILM_URI = 'http://localhost:8080/film';
const POST_URI = 'http://localhost:8080/';
/**
 * Récupère tous les films
 */
export function getAllFilms() {
    return axios.get(FILM_URI);
}

/**
 * Ajoute un nouveau film
 * @param {Object} film - Les données du film à ajouter
 */
export function postFilm(film) {
    return axios.post(POST_URI+"createFilm", film);
}


/**
 * Supprime un film
 * @param {string} filmId - L'ID du film à supprimer
 */
export function deleteFilm(filmId) {
    return axios.delete(POST_URI+"deleteFilm/"+filmId);
}
