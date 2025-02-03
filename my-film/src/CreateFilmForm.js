import { TextField, Select, MenuItem, Button } from '@mui/material';
import { useState, useEffect } from 'react';
import { getAllRealisateurs } from './api/RealisateurApi'; // Import de la méthode

export default function CreateFilmForm({ saveFilm }) {
    const [titre, setTitre] = useState('');
    const [duree, setDuree] = useState('');
    const [selectedRealisateur, setSelectedRealisateur] = useState('');
    const [realisateurs, setRealisateurs] = useState([]);

    useEffect(() => {
        getAllRealisateurs().then((response) => setRealisateurs(response.data));
    }, []);

    function handleCreateFilm(e) {
        e.preventDefault();

        if (!titre || !duree || !selectedRealisateur) {
            alert("Tous les champs doivent être remplis !");
            return;
        }

        saveFilm({ titre, duree, realisateurId: selectedRealisateur });
        window.location.reload();

        setTitre('');
        setDuree('');
        setSelectedRealisateur('');
    }

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
