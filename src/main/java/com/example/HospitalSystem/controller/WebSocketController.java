//package com.example.HospitalSystem.controller;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    @MessageMapping("/hello") // Maps incoming messages to "/app/hello"
//    @SendTo("/topic/greetings") // Sends return value to "/topic/greetings"
//    public String greeting(String message) {
//        return "Hello, " + message + "!";
//    }
//}