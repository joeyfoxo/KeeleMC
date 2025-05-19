package dev.joey.keelecore.api;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RankController {

    @PostMapping("/all-ranks")
    public ResponseEntity<List<String>> getAllRanks() {
        List<String> ranks = Collections.singletonList(PlayerRank.listRanks());
        return ResponseEntity.ok(ranks);
    }
}