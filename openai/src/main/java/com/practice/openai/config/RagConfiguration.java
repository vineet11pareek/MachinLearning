package com.practice.openai.config;

import org.apache.tika.Tika;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfiguration {

    @Bean
    public Tika getTika(){
        return new Tika();
    }

    @Bean
    public RetrievalAugmentationAdvisor questionAnswerAdvisor(VectorStore vectorStore){

        return RetrievalAugmentationAdvisor
                .builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever
                                .builder()
                                .topK(3)
                                .similarityThreshold(.5)
                                .vectorStore(vectorStore)
                                .build()
                ).build();
        //        return QuestionAnswerAdvisor
//                .builder(vectorStore)
//                .searchRequest(
//                        SearchRequest
//                                .builder()
//                                .topK(3)
//                                .similarityThreshold(.5)
//                                .build()
//                ).build();
    }
}
