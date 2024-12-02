import FilmCard from "./FilmCard";
import { useState } from "react";
import { useEffect } from "react";
import { getAllFilms } from "./api/FilmApi";

export default function FilmList() {
    const [films, setFilms] = useState([]);
    useEffect(() => {
    getAllFilms().then(reponse => {
    setFilms(reponse.data);
    }).catch(err => {
    console.log(err);
    })
    }, []);
    return films.map((film)=> {
    return <FilmCard key={film.id} film={film} />
    })
    }