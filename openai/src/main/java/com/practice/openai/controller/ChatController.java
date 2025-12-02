package com.practice.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatCleintBuilder){
        this.chatClient = chatCleintBuilder.build();
    }

    @GetMapping("/chat")
    public String getChat(@RequestParam("message") String message){
        return message;
    }
}
