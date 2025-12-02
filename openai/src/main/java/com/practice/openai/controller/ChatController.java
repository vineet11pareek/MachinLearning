package com.practice.openai.controller;

import com.practice.openai.service.LLMServices;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    LLMServices llmService;


    @GetMapping("/openai/chat")
    public String getOpenAiChat(@RequestParam("message") String message){
        return llmService.askOpenAI(message);
    }

    @GetMapping("/ollama/chat")
    public String getOllamaChat(@RequestParam("message") String message){
        return llmService.askOllama(message);
    }


}
