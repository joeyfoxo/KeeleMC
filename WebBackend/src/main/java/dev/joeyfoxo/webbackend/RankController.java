package dev.joeyfoxo.webbackend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/")
//@CrossOrigin(origins = {
//        "http://localhost",
//})
public class RankController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static String url = "http://localhost:5005"; // Use localhost, NOT the domain

    @GetMapping("/get-all-ranks")
    public ResponseEntity<List<String>> getRanksFromPlugin() {
        try {
            System.out.println("🔍 Calling Javalin API...");
            List<String> ranks = restTemplate.postForObject(
                    url + "get-all-ranks", null, List.class
            );
            System.out.println("✅ Got ranks: " + ranks);
            return ResponseEntity.ok(ranks);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }
}