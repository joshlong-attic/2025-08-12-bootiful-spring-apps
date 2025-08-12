package com.example.ability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class AbilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbilityApplication.class, args);
    }

}

@Controller
@ResponseBody
class HelloController {

    private final RestClient restClient;

    HelloController(RestClient.Builder restClient) {
        this.restClient = restClient.build();
    }

    @GetMapping("/vt")
    String fetch() {
        var threads = Thread.currentThread().toString() + ":";
        var response = this.restClient
                .get()
                .uri("http://localhost/delay/5")
                .retrieve()
                .body(String.class);
        threads += Thread.currentThread().toString();
        System.out.println(" " + threads);
        return response;
    }
}
