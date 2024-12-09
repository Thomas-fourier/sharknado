import FilmCard from "./FilmCard";

export default function FilmList({ films }) {
    return films.map((film) => {
        return <FilmCard key={film.id} film={film} />;
    });
}
