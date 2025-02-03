import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MovieIcon from '@mui/icons-material/Movie';
import { styled } from '@mui/material/styles';
import { Box } from '@mui/material';

const StyledAppBar = styled(AppBar)(({ theme }) => ({
    backgroundColor: '#4CAF50',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)', 
    borderRadius: '15px', 
    margin: '10px', 
}));

const StyledTypography = styled(Typography)(({ theme }) => ({
    fontFamily: 'Roboto, sans-serif',
    fontWeight: 'bold',
    color: '#FFFFFF', 
    textShadow: '2px 2px 4px rgba(0, 0, 0, 0.3)',
}));

export default function Header() {
    return (
        <StyledAppBar position="static">
            <Toolbar>
                <IconButton edge="start" color="inherit" aria-label="menu">
                    <MovieIcon sx={{ fontSize: 36, color: '#FFD700' }} /> {/* Icône de film en doré */}
                </IconButton>
                <StyledTypography variant="h5" component="div" sx={{ flexGrow: 1 }}>
                    Cinémathèque
                </StyledTypography>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                    {/* Vous pouvez ajouter d'autres éléments ici, comme des boutons de navigation */}
                </Box>
            </Toolbar>
        </StyledAppBar>
    );
}
