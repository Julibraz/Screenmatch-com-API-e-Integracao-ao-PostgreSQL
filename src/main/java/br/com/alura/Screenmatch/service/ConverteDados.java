package br.com.alura.Screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    public ConverteDados() {
    }

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return (T)this.mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desserializar JSON", e);
        }
    }
}
