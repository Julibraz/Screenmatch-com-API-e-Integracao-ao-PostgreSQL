package br.com.alura.Screenmatch.service;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public ConsultaChatGPT() {
    }

    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));
        CompletionRequest requisicao = CompletionRequest.builder().model("gpt-3.5-turbo").prompt("traduza para o português o texto: " + texto).maxTokens(1000).temperature(0.7).build();
        CompletionResult resposta = service.createCompletion(requisicao);
        return ((CompletionChoice)resposta.getChoices().get(0)).getText();
    }
}