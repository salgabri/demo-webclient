package com.example.webclient.demowebclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MultiRequestController {

    @GetMapping(path = "spam")
    public void spam() {

        Long startTime = System.currentTimeMillis();
        List<String> results = new LinkedList<>();
        List<Mono<String>> monos = new LinkedList<>();
        WebClient client = WebClient.builder().baseUrl("http://localhost:8081").build();
        for(int i = 0; i < 300; i++){
            Mono<String> mono = client.get().uri(String.join("","/hello")).retrieve().bodyToMono(String.class);
            monos.add(mono);
        }
        Flux.merge(monos).blockLast();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
