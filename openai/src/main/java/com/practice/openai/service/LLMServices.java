package com.practice.openai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LLMServices {
    private final ChatClient openAI;
    private final ChatClient ollama;

    public LLMServices(
            @Qualifier("openAIClient") ChatClient openAI,
            @Qualifier("ollamaClient") ChatClient ollama) {
        this.openAI = openAI;
        this.ollama = ollama;
    }

    public String askOpenAI(String question) {
        return openAI.prompt(question).call().content();
    }

    public String askOllama(String question) {
        return ollama.prompt(question).call().content();
    }

}
