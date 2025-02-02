import Typography from '@mui/material/Typography';
import { Card, IconButton, TextField, Select, MenuItem, Button } from '@mui/material';
import CardContent from '@mui/material/CardContent';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { deleteFilm, postFilm } from './api/FilmApi'
import { getAllRealisateurs } from './api/RealisateurApi'; // Import de la méthode
import { useEffect, useState } from 'react';
import React from 'react';


function onConfirmDeleteFilm(setIsVisible, id) {
    deleteFilm(id);
    setIsVisible(false);
}


const ModalDeleteFilm = ({ isOpen, setIsVisible, id, setIsModalOpen }) => {
  if (!isOpen) return null;

  return (
    <div style={styles.modal}>
        <h2>Êtes vous sûr de vouloir supprimer le film?</h2>
        <div>
          <button onClick={() => onConfirmDeleteFilm(setIsVisible, id)}>Confirmer</button>
          <button onClick={() => setIsModalOpen(false)}>Annuler</button>
        </div>
    </div>
  );
};

function EditFilmForm({film, setFilm, setModalOpen, setFilmDuree, setFilmTitre, setFilmId}) {
    const [titre, setTitre] = useState(film.titre);
    const [duree, setDuree] = useState(film.duree);
    const [selectedRealisateur, setSelectedRealisateur] =
            useState('');
    const [realisateurs, setRealisateurs] = useState([]);

    // Why should that make sense?
    if (selectedRealisateur.length === 0) {
        getAllRealisateurs().then((response) => {
            setRealisateurs(response.data);
            setSelectedRealisateur(film.realisateur.id);
        });
    }

    // Méthode appelée lors de la soumission du formulaire
    function handleEditFilm(e) {

        // Vérification que tous les champs sont remplis
        if (!titre || !duree || !selectedRealisateur) {
            alert("Tous les champs doivent être remplis !");
            return;
        }


        deleteFilm(film.id).then(
            (res) => {
                postFilm({ titre, duree, realisateurId: selectedRealisateur }).then(
                    (res) => {
                        console.log(res);
                        setFilm(res.data);
                        setDuree(res.data.duree);
                        setFilmId(res.data.id);
                        setFilmTitre(res.data.titre);
                    })
            });

        // fermer le modal
        setModalOpen(false);

        // Mettre à jour le contenu de la slide


    }

    return (
        <form onSubmit={handleEditFilm}>
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
                Modifier le film
            </Button>
            <Button onClick={() => setModalOpen(false)}>
                Annuler la modification
            </Button>
        </form>
    );
}

const ModalEditFilm = ({ isOpen, film, setIsModalOpen, setFilm, setFilmDuree, setFilmTitre, setFilmId }) => {
    if (!isOpen) return null;
  
    return (
      <div style={styles.modal}>
          <h2>Modifier le film</h2>
          <div>
            <EditFilmForm
                film={film}
                setFilm={setFilm}
                setModalOpen={setIsModalOpen}
                setFilmDuree={setFilmDuree}
                setFilmTitre={setFilmTitre}
                setFilmId={setFilmId}
            />
          </div>
      </div>
    );
  };

const styles = {
  overlay: {
    position: 'fixed',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    backgroundColor: 'rgba(0,0,0,0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
    modal: {
      backgroundColor: 'white',
      padding: '20px',
      borderRadius: '8px',
      boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
      width: '300px',
      textAlign: 'center',
    }
}


export default function FilmCard(props) {
    const [isVisible, setIsVisible] = useState(true);
    const [film, setFilm] = useState(props.film);
    const [filmTitre, setFilmTitre] = useState(props.film.titre);
    const [filmDuree, setFilmDuree] = useState(props.film.duree);
    const [filmId, setFilmId] = useState(props.film.id);
    const [realisateur, setRealisateur] = useState(null);
    const [isModalOpenDeleteFilm, setIsModalOpenDeleteFilm] = useState(false);
    const [isModalOpenEditFilm, setIsModalOpenEditFilm] = useState(false);

    // Charger le réalisateur associé au film
    useEffect(() => {
        getAllRealisateurs().then((response) => {
            const realisateurAssocie = response.data.find(r => r.id === film.realisateur.id);
            setRealisateur(realisateurAssocie);
        });
    }, [film]);

    return (
        <div>
        {isVisible && (
            <Card variant="outlined">
                <CardContent>
                    <Typography variant="h5" gutterBottom>
                        {filmTitre}
                    </Typography>
                    <Typography variant="body1">
                        {filmDuree} minutes
                    </Typography>
                    {realisateur && (
                        <Typography variant="body2">
                            Réalisé par {realisateur.prenom} {realisateur.nom} {realisateur.celebre && '⭐'}
                        </Typography>
                    )}
                    <IconButton onClick={() => {setIsModalOpenDeleteFilm(!isModalOpenDeleteFilm); setIsModalOpenEditFilm(false)}}>
                        <DeleteIcon />
                    </IconButton>
                    <IconButton onClick={() => {setIsModalOpenEditFilm(!isModalOpenEditFilm); setIsModalOpenDeleteFilm(false)}}>
                        <EditIcon />
                    </IconButton>
                    <ModalDeleteFilm
                        isOpen={isModalOpenDeleteFilm}
                        setIsVisible={setIsVisible} 
                        id={filmId} 
                        setIsModalOpen={setIsModalOpenDeleteFilm}
                    />
                    <ModalEditFilm
                        isOpen={isModalOpenEditFilm}
                        film={film}
                        setIsModalOpen={setIsModalOpenEditFilm}
                        setFilm={setFilm}
                        setFilmDuree={setFilmDuree}
                        setFilmTitre={setFilmTitre}
                        setFilmId={setFilmId}
                    />
                </CardContent>
            </Card>
        )}
        </div>
    );
}