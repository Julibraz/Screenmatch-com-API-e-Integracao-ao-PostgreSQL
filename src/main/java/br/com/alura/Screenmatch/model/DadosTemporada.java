package br.com.alura.Screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DadosTemporada(Integer numero, List<DadosEpisodio> episodios) {
    public DadosTemporada(@JsonAlias({"Season"}) Integer numero, @JsonAlias({"Episodes"}) List<DadosEpisodio> episodios) {
        this.numero = numero;
        this.episodios = episodios;
    }

    @JsonAlias({"Season"})
    public Integer numero() {
        return this.numero;
    }

    @JsonAlias({"Episodes"})
    public List<DadosEpisodio> episodios() {
        return this.episodios;
    }
}