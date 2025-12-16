package com.practice.openai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class RagServices {

    private final ChatClient chatMemoryOpenAIClient;
    private final VectorStore vectorStore;

    @Value("classpath:/templates/resumePromptTemplate.st")
    private Resource resumePromptResource;

    public RagServices(@Qualifier("chatMemoryOpenAIClient") ChatClient chatMemoryOpenAIClient,
                       VectorStore vectorStore) {
        this.chatMemoryOpenAIClient = chatMemoryOpenAIClient;
        this.vectorStore = vectorStore;
    }


    public String askOpenAIWithRag(String message, String username) {
//        SearchRequest searchRequest = SearchRequest
//                .builder()
//                .query(message)
//                .topK(3)
//                .similarityThreshold(0.5)
//                .build();
//        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//        String similarContext = similarDocs.stream()
//                .map(Document::getText)
//                .collect(Collectors.joining(System.lineSeparator()));

        return chatMemoryOpenAIClient
                .prompt()
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
    }
}
