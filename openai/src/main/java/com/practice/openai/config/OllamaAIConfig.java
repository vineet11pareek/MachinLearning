package com.practice.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OllamaAIConfig {



    @Bean
    @Qualifier("ollamaClient")
    public ChatClient ollamaChatCleint(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }

    /*
    creating bean with chat memory to keep message memory
     */
    @Bean
    @Qualifier("chatMemoryOllamaClient")
    public ChatClient chatMemoryClient(OllamaChatModel ollamaChatModel, ChatMemory chatMemory){
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        return ChatClient
                .builder(ollamaChatModel)
                .defaultAdvisors(List.of(loggerAdvisor,memoryAdvisor))
                .build();
    }
}
