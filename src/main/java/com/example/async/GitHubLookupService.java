package com.example.async;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupService {
    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        String url = String.format("https://api.github.com/users/%s", user);

        System.out.println("Execute method asynchronously - "
            + Thread.currentThread().getName());

        User result = restTemplate.getForObject(url, User.class);

        Thread.sleep(1000);

        return CompletableFuture.completedFuture(result);
    }
}