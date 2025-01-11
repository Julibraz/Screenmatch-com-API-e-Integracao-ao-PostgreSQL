package br.com.alura.Screenmatch.service.TraducaoMyMemory;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DadosTraducao(DadosResposta dadosResposta) {
    public DadosTraducao(@JsonAlias({"responseData"}) DadosResposta dadosResposta) {
        this.dadosResposta = dadosResposta;
    }

    @JsonAlias({"responseData"})
    public DadosResposta dadosResposta() {
        return this.dadosResposta;
    }
}