package dev.joeyfoxo.webbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/ranks")
public class RankController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/all-ranks")
    public ResponseEntity<List<String>> getRanksFromPlugin() {
        try {
            List<String> ranks = restTemplate.postForObject(
                    "http://localhost:5005/api/all-ranks",
                    null,
                    List.class
            );
            return ResponseEntity.ok(ranks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }
}