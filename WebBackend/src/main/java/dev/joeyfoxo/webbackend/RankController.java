package dev.joeyfoxo.webbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class RankController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static String url = "http://localhost:5005/api/";

    @GetMapping("/get-all-ranks")
    public ResponseEntity<List<String>> getRanksFromPlugin() {
        try {
            System.out.println("🔍 Calling Javalin API...");
            List<String> ranks = restTemplate.getForObject(
                    url + "get-all-ranks", List.class
            );
            System.out.println("✅ Got ranks: " + ranks);
            return ResponseEntity.ok(ranks);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to call Javalin API: " + e.getMessage());
            return ResponseEntity.status(500).body(List.of());
        }
    }
}