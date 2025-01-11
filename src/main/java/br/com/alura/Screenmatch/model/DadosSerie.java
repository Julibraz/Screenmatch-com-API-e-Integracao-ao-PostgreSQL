package br.com.alura.Screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DadosSerie(String titulo, Integer totalTemporadas, String avaliacao, String genero, String atores, String poster, String sinopse) {
    public DadosSerie(@JsonAlias({"Title"}) String titulo, @JsonAlias({"totalSeasons"}) Integer totalTemporadas, @JsonAlias({"imdbRating"}) String avaliacao, @JsonAlias({"Genre"}) String genero, @JsonAlias({"Actors"}) String atores, @JsonAlias({"Poster"}) String poster, @JsonAlias({"Plot"}) String sinopse) {
        this.titulo = titulo;
        this.totalTemporadas = totalTemporadas;
        this.avaliacao = avaliacao;
        this.genero = genero;
        this.atores = atores;
        this.poster = poster;
        this.sinopse = sinopse;
    }

    @JsonAlias({"Title"})
    public String titulo() {
        return this.titulo;
    }

    @JsonAlias({"totalSeasons"})
    public Integer totalTemporadas() {
        return this.totalTemporadas;
    }

    @JsonAlias({"imdbRating"})
    public String avaliacao() {
        return this.avaliacao;
    }

    @JsonAlias({"Genre"})
    public String genero() {
        return this.genero;
    }

    @JsonAlias({"Actors"})
    public String atores() {
        return this.atores;
    }

    @JsonAlias({"Poster"})
    public String poster() {
        return this.poster;
    }

    @JsonAlias({"Plot"})
    public String sinopse() {
        return this.sinopse;
    }
}
