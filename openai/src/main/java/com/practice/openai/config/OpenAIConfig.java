package com.practice.openai.config;

import com.practice.openai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAIConfig {
    @Bean
    @Qualifier("openAICleint")
    public ChatClient openAIClient(OpenAiChatModel openAiChatmodel) {
        return ChatClient
                .builder((openAiChatmodel))
                .defaultAdvisors(List.of(new TokenUsageAuditAdvisor(),new SimpleLoggerAdvisor()))
                .build();
    }

    @Bean
    @Qualifier("jdbcChatMemory")
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository){
        return MessageWindowChatMemory
                .builder()
                .maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();
    }

    @Bean
    @Qualifier("chatMemoryOpenAIClient")
    public ChatClient chatMemoryOpenAIClient(OpenAiChatModel openAiChatmodel, @Qualifier("jdbcChatMemory") ChatMemory chatMemory){
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        return ChatClient
                .builder(openAiChatmodel)
                .defaultAdvisors(List.of(loggerAdvisor,memoryAdvisor))
                .build();
    }
}
