package com.musicfy.controller;

import com.musicfy.service.SpotifyRecommendationService;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final SpotifyRecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<Recommendations> getRecommendations(
            @RequestParam(required = false) List<String> seedArtists,
            @RequestParam(required = false) List<String> seedGenres,
            @RequestParam(required = false) List<String> seedTracks,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "US") String market,
            @RequestParam(required = false) Float minPopularity,
            @RequestParam(required = false) Float maxPopularity,
            @RequestParam(required = false) Float targetPopularity,
            @RequestParam(required = false) Float minEnergy,
            @RequestParam(required = false) Float maxEnergy,
            @RequestParam(required = false) Float targetEnergy,
            @RequestParam(required = false) Float minDanceability,
            @RequestParam(required = false) Float maxDanceability,
            @RequestParam(required = false) Float targetDanceability) {
        return ResponseEntity.ok(recommendationService.getRecommendations(
                seedArtists, seedGenres, seedTracks, limit, market,
                minPopularity, maxPopularity, targetPopularity,
                minEnergy, maxEnergy, targetEnergy,
                minDanceability, maxDanceability, targetDanceability));
    }

    @GetMapping("/track/{trackId}")
    public ResponseEntity<Track[]> getRecommendationsForTrack(
            @PathVariable String trackId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "US") String market) {
        return ResponseEntity.ok(recommendationService.getRecommendationsForTrack(trackId, limit, market));
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Track[]> getRecommendationsForArtist(
            @PathVariable String artistId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "US") String market) {
        return ResponseEntity.ok(recommendationService.getRecommendationsForArtist(artistId, limit, market));
    }
} 