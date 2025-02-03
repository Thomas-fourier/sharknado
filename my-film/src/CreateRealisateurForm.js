import { TextField, Button } from '@mui/material';
import { useState } from 'react';

export default function CreateRealisateurForm({ saveRealisateur }) {
    const [nom, setNom] = useState('');
    const [prenom, setPrenom] = useState('');
    const [dateNaissance, setDateNaissance] = useState('');

    // Méthode pour gérer la soumission du formulaire
    function handleCreateRealisateur(e) {
        e.preventDefault();

        // Vérification que tous les champs sont remplis
        if (!nom || !prenom || !dateNaissance) {
            alert("Tous les champs doivent être remplis !");
            return;
        }

        // Appeler la fonction onSubmit passée en prop
        saveRealisateur({ nom, prenom, dateNaissance });
        window.location.reload();

        // Réinitialiser les champs après la soumission
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
