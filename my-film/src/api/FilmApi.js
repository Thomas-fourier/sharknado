import axios from 'axios';
const FILM_URI = 'http://localhost:8080/film'
export function getAllFilms(){
return axios.get(FILM_URI);
}