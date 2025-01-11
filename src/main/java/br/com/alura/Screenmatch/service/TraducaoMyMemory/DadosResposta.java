package br.com.alura.Screenmatch.service.TraducaoMyMemory;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DadosResposta(String textoTraduzido) {
    public DadosResposta(@JsonAlias({"translatedText"}) String textoTraduzido) {
        this.textoTraduzido = textoTraduzido;
    }

    @JsonAlias({"translatedText"})
    public String textoTraduzido() {
        return this.textoTraduzido;
    }
}

