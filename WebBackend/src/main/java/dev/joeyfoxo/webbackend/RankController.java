package dev.joeyfoxo.webbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost")
public class RankController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static String url = "http://localhost:5005/api/";

    @GetMapping("/get-all-ranks")
    public ResponseEntity<List<String>> getRanksFromPlugin() {
        try {
            List<String> ranks = restTemplate.postForObject(
                    url + "all-ranks",
                    null,
                    List.class
            );
            return ResponseEntity.ok(ranks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }

    @PostMapping("/all-ranks")
    public ResponseEntity<List<String>> postRanksFromPlugin() {
        try {
            List<String> ranks = restTemplate.postForObject(
                    url + "all-ranks",
                    null,
                    List.class
            );
            return ResponseEntity.ok(ranks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }
}