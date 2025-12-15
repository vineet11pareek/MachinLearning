package com.practice.openai.controller;

import com.practice.openai.service.OllamaServices;
import com.practice.openai.service.OpenAIServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ollama")
public class OllamaController {

    @Autowired
    OllamaServices ollamaService;

    @GetMapping("/chat")
    public String getOllamaChat(@RequestParam("message") String message) {
        return ollamaService.askOllama(message);
    }

    @GetMapping("/email")
    public String getOllamaChat(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage) {
        return ollamaService.askOllamaForEmail(customerName,customerMessage);
    }

    @GetMapping("/policies")
    public String getOllamapolicies(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage) {
        return ollamaService.askOllamaForPolicies(customerName,customerMessage);
    }
    @GetMapping("/chat-memory")
    public String getOllamaWIthMemory(@RequestParam("message") String message) {
        return ollamaService.askOllamaWithMemory(message);
    }
}
