package com.coders.mainservice.controller;

import com.coders.mainservice.model.Document;
import com.coders.mainservice.model.DocumentList;
import com.coders.mainservice.model.Validator;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class MainController {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(MainController.class);

    private final WebClient.Builder webClientBuilder;

    public MainController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/api")
    public String api(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "api";
    }

    @PostMapping("/api")
    public String mainApi(
            @RequestParam String certificate,
            @RequestParam String document,
            @RequestParam String outputs,
            Principal principal,
            Model model) {

        List<Mono<Validator>> validatorMonoList = new ArrayList<>();
        List<String> documentList = Arrays.stream(document.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());

        for (int i = 0; i < documentList.size(); i++) {
            String tempDocument = documentList.get(i);
            Mono<Validator> validatorMono = webClientBuilder
                    .baseUrl("http://document-service")
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/document")
                            .queryParam("certificate", certificate)
                            .queryParam("document", tempDocument)
                            .queryParam("outputs", outputs)
                            .queryParam("username", principal.getName())
                            .build())
                    .retrieve()
                    .bodyToMono(Validator.class);

            validatorMonoList.add(validatorMono);
        }

        Object[] responses = Mono.zip(validatorMonoList, x -> x).block();

        for (int i = 0; i < responses.length; i++) {
            Validator validator = (Validator) responses[i];
            validator.getValidationMap().forEach((key, value) -> {
                model.addAttribute(key, value);
            });
        }

        if (model.asMap().size() > 0) {
            model.addAttribute("username", principal.getName());
            return "/api";
        } else {
            return "redirect:/all-records";
        }
    }

    @GetMapping("/all-records")
    public String allRecords(Principal principal, Model model) {
        String username = principal.getName();

        DocumentList documentList = webClientBuilder
                .baseUrl("http://document-service")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/all-records")
                        .queryParam("username", principal.getName())
                        .build())
                .retrieve()
                .bodyToMono(DocumentList.class)
                .block();

        List<Document> allRecords = documentList.getDocumentList();
        model.addAttribute("username", username);
        model.addAttribute("allRecords", allRecords);
        return "all-records";
    }
}
