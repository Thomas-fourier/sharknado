import { TextField, Select, MenuItem, Button } from '@mui/material';
import { useState, useEffect } from 'react';
import { getAllRealisateurs } from './api/RealisateurApi'; // Import de la méthode
import { postFilm } from './api/FilmApi'; // Import de la méthode

export default function CreateFilmForm() {
    const [titre, setTitre] = useState('');
    const [duree, setDuree] = useState('');
    const [selectedRealisateur, setSelectedRealisateur] = useState('');
    const [realisateurs, setRealisateurs] = useState([]);

    // Récupérer les réalisateurs au démarrage
    useEffect(() => {
        getAllRealisateurs().then((response) => setRealisateurs(response.data));
    }, []);

    // Méthode appelée lors de la soumission du formulaire
    function handleCreateFilm(e) {
        e.preventDefault();

        // Vérification que tous les champs sont remplis
        if (!titre || !duree || !selectedRealisateur) {
            alert("Tous les champs doivent être remplis !");
            return;
        }

        // Création du film en appelant la méthode postFilm
        postFilm({ titre: titre, duree: duree, realisateurId: selectedRealisateur })
            .then((response) => {
                alert("Film ajouté avec succès !");
                // Réinitialiser les champs après la soumission
                setTitre('');
                setDuree('');
                setSelectedRealisateur('');
            })
            .catch((error) => {
                alert("Erreur lors de l'ajout du film: " + error.message);
            });
    }
    console.log(realisateurs);
    return (
        <form onSubmit={handleCreateFilm}>
            <TextField
                label="Titre"
                value={titre}
                onChange={(e) => setTitre(e.target.value)}
                fullWidth
                required
            />
            <TextField
                label="Durée"
                type="number"
                value={duree}
                onChange={(e) => setDuree(e.target.value)}
                fullWidth
                required
            />
            <Select
                value={selectedRealisateur}
                onChange={(e) => setSelectedRealisateur(e.target.value)}
                fullWidth
                required
            >
                {realisateurs.map((realisateur) => (
                    <MenuItem key={realisateur.id} value={realisateur.id}>
                        {realisateur.prenom} {realisateur.nom}
                    </MenuItem>
                ))}
            </Select>
            <Button type="submit" variant="contained" fullWidth>
                Ajouter un film
            </Button>
        </form>
    );
}
