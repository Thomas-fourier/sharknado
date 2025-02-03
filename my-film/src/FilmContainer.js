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

    function handleCreateFilm(filmData) {
        postFilm(filmData)
            .then((response) => {
                alert("Film ajouté avec succès !");
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
        <div style={{
            minHeight: '100vh',
            background: 'linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)',
            padding: '20px 0'
        }}>
            {/* En-tête */}
            <div style={{
                textAlign: 'center',
                padding: '40px 20px',
                marginBottom: '30px'
            }}>
                <h1 style={{
                    color: 'white',
                    fontSize: '2.5rem',
                    fontWeight: '700',
                    textShadow: '2px 2px 4px rgba(0,0,0,0.3)',
                    letterSpacing: '1.5px',
                    margin: 0
                }}>
                    Cinémathèque
                </h1>
                <p style={{
                    color: 'rgba(255,255,255,0.9)',
                    fontSize: '1.2rem',
                    marginTop: '10px'
                }}>
                    La streaming à la vitesse de la lumière ⚡
                </p>
            </div>

            {/* Contenu principal */}
            <div style={{
                maxWidth: 1200,
                margin: '0 auto',
                padding: '0 20px',
                display: 'flex',
                gap: '40px',
                flexDirection: 'column'
            }}>
                {/* Section Liste de films */}
                <section style={{
                    backgroundColor: 'rgba(255,255,255,0.95)',
                    borderRadius: '15px',
                    padding: '25px',
                    boxShadow: '0 8px 30px rgba(0,0,0,0.2)'
                }}>
                    <FilmList films={films} />
                </section>

                {/* Section Création */}
                <div style={{
                    display: 'flex',
                    gap: '30px',
                    flexWrap: 'wrap'
                }}>
                    {/* Formulaire Film */}
                    <div style={{
                        flex: 1,
                        minWidth: 300,
                        backgroundColor: 'rgba(255,255,255,0.95)',
                        borderRadius: '15px',
                        padding: '25px',
                        boxShadow: '0 8px 30px rgba(0,0,0,0.2)'
                    }}>
                        <h3 style={{ 
                            marginTop: 0, 
                            color: '#1e3c72',
                            fontSize: '1.5rem',
                            borderBottom: '2px solid #1e3c72',
                            paddingBottom: '10px'
                        }}>
                            Ajouter un Film
                        </h3>
                        <CreateFilmForm saveFilm={handleCreateFilm} />
                    </div>

                    {/* Formulaire Réalisateur */}
                    <div style={{
                        flex: 1,
                        minWidth: 300,
                        backgroundColor: 'rgba(255,255,255,0.95)',
                        borderRadius: '15px',
                        padding: '25px',
                        boxShadow: '0 8px 30px rgba(0,0,0,0.2)'
                    }}>
                        <h3 style={{ 
                            marginTop: 0, 
                            color: '#1e3c72',
                            fontSize: '1.5rem',
                            borderBottom: '2px solid #1e3c72',
                            paddingBottom: '10px'
                        }}>
                            Ajouter un Réalisateur
                        </h3>
                        <CreateRealisateurForm saveRealisateur={handleCreateRealisateur} />
                    </div>
                </div>
            </div>
        </div>
    );
}