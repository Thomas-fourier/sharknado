import { useState, useEffect } from "react";
import FilmList from './FilmList.js';
import CreateFilmForm from './CreateFilmForm.js';
import CreateRealisateurForm from './CreateRealisateurForm.js';
import { getAllFilms, postFilm } from "./api/FilmApi";
import { postRealisateur } from "./api/RealisateurApi";

export default function FilmContainer() {
    const [films, setFilms] = useState([]);

    useEffect(() => {
        getAllFilms().then(response => {
            setFilms(response.data);
        }).catch(err => {
            console.log(err);
        });
    }, []);

    // Fonction pour gérer la soumission du formulaire de création de film
    function handleCreateFilm(filmData) {
        postFilm(filmData)
            .then((response) => {
                alert("Film ajouté avec succès !");
                // Mettre à jour la liste des films après l'ajout
                setFilms([...films, response.data]);
            })
            .catch((error) => {
                alert("Erreur lors de l'ajout du film: " + error.message);
            });
    }

    function handleCreateRealisateur(realisateurData) {
        postRealisateur(realisateurData)
            .then(() => {
                alert("Réalisateur ajouté avec succès !");
            })
            .catch((error) => {
                alert("Erreur lors de l'ajout du réalisateur: " + error.message);
            });
    }

    return (
        <>
            <FilmList films={films} />
            <CreateFilmForm saveFilm={handleCreateFilm} />
            <CreateRealisateurForm saveRealisateur={handleCreateRealisateur} />
        </>
    );
}
