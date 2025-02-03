import { TextField, Button } from '@mui/material';
import { useState } from 'react';

export default function CreateRealisateurForm({ saveRealisateur }) {
    const [nom, setNom] = useState('');
    const [prenom, setPrenom] = useState('');
    const [dateNaissance, setDateNaissance] = useState('');

    function handleCreateRealisateur(e) {
        e.preventDefault();

        if (!nom || !prenom || !dateNaissance) {
            alert("Tous les champs doivent être remplis !");
            return;
        }

        saveRealisateur({ nom, prenom, dateNaissance });
        window.location.reload();

        setNom('');
        setPrenom('');
        setDateNaissance('');
    }

    return (
        <form onSubmit={handleCreateRealisateur}>
            <TextField
                label="Nom"
                value={nom}
                onChange={(e) => setNom(e.target.value)}
                fullWidth
                required
            />
            <TextField
                label="Prénom"
                value={prenom}
                onChange={(e) => setPrenom(e.target.value)}
                fullWidth
                required
            />
            <TextField
                label="Date de naissance"
                type="date"
                value={dateNaissance}
                onChange={(e) => setDateNaissance(e.target.value)}
                fullWidth
                required
                InputLabelProps={{ shrink: true }}
            />
            <Button type="submit" variant="contained" fullWidth>
                Ajouter un réalisateur
            </Button>
        </form>
    );
}
