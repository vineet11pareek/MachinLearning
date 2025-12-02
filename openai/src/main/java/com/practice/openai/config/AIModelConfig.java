package com.practice.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIModelConfig {

    @Bean
    @Qualifier("openAICleint")
    public ChatClient openAIClient(OpenAiChatModel openAiChatmodel){
        return ChatClient.builder((openAiChatmodel)).build();
    }

    @Bean
    @Qualifier("ollamaClient")
    public ChatClient ollamaChatCleint(OllamaChatModel ollamaChatModel){
        return ChatClient.builder(ollamaChatModel).build();
    }
}
