package com.example.async;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Arrays.asList;

@RestController
public class GihHubLookupContoller {
    private final GitHubLookupService gitHubLookupService;

    public GihHubLookupContoller(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @GetMapping
    public DeferredResult<ResponseEntity<?>> getGitHubUsersInfo() throws InterruptedException, ExecutionException {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        CompletableFuture<User> firstUser = gitHubLookupService.findUser("rade-milovic-devtech");
        CompletableFuture<User> secondUser = gitHubLookupService.findUser("rmilovic90");

        CompletableFuture.allOf(firstUser, secondUser).join();

        deferredResult.setResult(
            ResponseEntity.ok(
                asList(
                    firstUser.get(),
                    secondUser.get()
                )
            )
        );

        return deferredResult;
    }
}