import Typography from '@mui/material/Typography';
import { Card, IconButton } from '@mui/material';
import CardContent from '@mui/material/CardContent';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

export default function FilmCard(props) {
    const handleClickOnDeleteButton = () => {
        console.log("delete");
      }
    
      const handleClickOnEditButton = () => {
        console.log("edit");
      }

    return (
        <Card variant="outlined">
            <CardContent>
                <Typography variant="h5" gutterBottom>
                    {props.film.titre}
                </Typography>
                <Typography variant="body1">
                    {props.film.duree} minutes
                </Typography>
                <IconButton onClick={handleClickOnDeleteButton}>
                    <DeleteIcon />
                </IconButton>
                <IconButton onClick={handleClickOnEditButton}>
                    <EditIcon />
                </IconButton>
            </CardContent>
        </Card>
    );
}