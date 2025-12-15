package com.practice.openai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OllamaServices {
    private final ChatClient ollamaClient;
    private final ChatClient chatMemoryClient;
    private final String systemRole = """
            You are a professional customer service assistant which helps drafting email
            responses to improve the productivity of the customer support team
            """;

    @Value("classpath:/templates/userPromptTemplate.st")
    private Resource userPromptResource;

    @Value("classpath:/templates/systemPromptTemplate.st")
    private Resource systemPromptResource;


    public OllamaServices(
            @Qualifier("ollamaClient") ChatClient ollama,
            @Qualifier("chatMemoryOllamaClient")ChatClient chatMemoryClient) {
        this.ollamaClient = ollama;
        this.chatMemoryClient = chatMemoryClient;
    }

    public String askOllama(String question) {
        ollamaClient.prompt(question).call().content();
        return ollamaClient.prompt(question).call().content();
    }

    public String askOllamaForEmail(String customerName, String customerMessage) {
        return ollamaClient
                .prompt()
                .system(systemRole)
                .user(userPromptSpec -> userPromptSpec.text(userPromptResource)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage))
                .call()
                .content();
    }

    public String askOllamaForPolicies(String customerName, String customerMessage) {
        return ollamaClient
                .prompt()
                .system(systemPromptResource)
                .user(userPromptSpec -> userPromptSpec.text(userPromptResource)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage))
                .call()
                .content();
    }
    public String askOllamaWithMemory(String question){
        return chatMemoryClient
                .prompt(question)
                .call()
                .content();
    }
}
