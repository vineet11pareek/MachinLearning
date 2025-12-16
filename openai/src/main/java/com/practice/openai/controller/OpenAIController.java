package com.practice.openai.controller;

import com.practice.openai.service.OpenAIServices;
import com.practice.openai.service.RagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    @Autowired
    OpenAIServices llmService;

    RagServices ragServices;

    public OpenAIController(OpenAIServices llmService, RagServices ragServices) {
        this.llmService = llmService;
        this.ragServices = ragServices;
    }

    @GetMapping("/chat")
    public String getOpenAiChat(@RequestParam("message") String message){
        return llmService.askOpenAI(message);
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<String> getOpenAiChatWithMemory(@RequestParam("message") String message, @RequestParam("username") String username){
        return ResponseEntity.ok(llmService.askOpenAIWithMemory(message,username));
    }

    @GetMapping("/chat-rag")
    public ResponseEntity<String> getOpenAiChatWithRag(@RequestParam("message") String message, @RequestParam("username") String username){
        return ResponseEntity.ok(ragServices.askOpenAIWithRag(message, username));
    }


}
