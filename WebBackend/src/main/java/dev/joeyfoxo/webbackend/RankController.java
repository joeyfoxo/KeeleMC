package dev.joeyfoxo.webbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://rank.joeyfox.dev"})
public class RankController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String JAVALIN_API_BASE = "http://localhost:5005/api";

    // Generic helper for GET requests
    private <T> ResponseEntity<T> genericGetforwardToJavalin(String path, Class<T> responseType) {
        try {
            String fullUrl = JAVALIN_API_BASE + path;
            System.out.println("🔍 Proxying to: " + fullUrl);
            T result = restTemplate.getForObject(fullUrl, responseType);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println("❌ Error calling Javalin: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/get-all-ranks")
    public ResponseEntity<List> getAllRanks() {
        return genericGetforwardToJavalin("/get-all-ranks", List.class);
    }

    @GetMapping("/get-player-rank/{uuid}")
    public ResponseEntity<Map> getPlayerRank(@PathVariable String uuid) {
        return genericGetforwardToJavalin("/get-player-rank/" + uuid, Map.class);
    }
}