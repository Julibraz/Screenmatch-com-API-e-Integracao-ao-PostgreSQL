package br.com.alura.Screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DadosEpisodio(String titulo, Integer numero, String avaliacao, String dataLancamento) {
    public DadosEpisodio(@JsonAlias({"Title"}) String titulo, @JsonAlias({"Episode"}) Integer numero, @JsonAlias({"imdbRating"}) String avaliacao, @JsonAlias({"Released"}) String dataLancamento) {
        this.titulo = titulo;
        this.numero = numero;
        this.avaliacao = avaliacao;
        this.dataLancamento = dataLancamento;
    }

    @JsonAlias({"Title"})
    public String titulo() {
        return this.titulo;
    }

    @JsonAlias({"Episode"})
    public Integer numero() {
        return this.numero;
    }

    @JsonAlias({"imdbRating"})
    public String avaliacao() {
        return this.avaliacao;
    }

    @JsonAlias({"Released"})
    public String dataLancamento() {
        return this.dataLancamento;
    }
}