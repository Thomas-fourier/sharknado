import mockFilms from "./mock/FilmMock";
import FilmCard from "./FilmCard";

export default function FilmList() {
    const films= mockFilms;
    return films.map((film)=> {
    return <FilmCard key={film.id} film={film} />
    })
    }