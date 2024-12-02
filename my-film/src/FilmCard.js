import Typography from '@mui/material/Typography';
import { Card } from '@mui/material';
import CardContent from '@mui/material/CardContent';

export default function FilmCard(props) {
    return <Card variant="outlined">
        <CardContent>
            <Typography variant="h5" gutterBottom>
                {props.film.titre}
            </Typography>
            <Typography variant="body1">
                {props.film.duree} minutes
            </Typography>
        </CardContent>
    </Card>
    ;
}