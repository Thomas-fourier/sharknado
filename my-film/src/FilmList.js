

export default function FilmList() {
    const films= ["film1", "film2", "film3"];
    return films.map((film)=> {
    return <h1>{film}</h1>
    })
    }