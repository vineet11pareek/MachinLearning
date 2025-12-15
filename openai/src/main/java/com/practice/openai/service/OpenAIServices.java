package com.practice.openai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class OpenAIServices {
    private final ChatClient openAI;
    private final ChatClient chatMemoryOpenAIClient;

    public OpenAIServices(
            @Qualifier("openAIClient") ChatClient openAI, @Qualifier("chatMemoryOpenAIClient")ChatClient chatMemoryOpenAIClient) {
        this.openAI = openAI;
        this.chatMemoryOpenAIClient = chatMemoryOpenAIClient;
    }

    public String askOpenAI(String question) {
        return openAI
                .prompt(question)
                .call()
                .content();
    }

    public String askOpenAIWithMemory(String message,String username){
        return chatMemoryOpenAIClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,username))
                .call()
                .content();
    }

}
