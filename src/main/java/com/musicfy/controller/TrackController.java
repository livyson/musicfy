package com.musicfy.controller;

import com.musicfy.service.SpotifyTrackService;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final SpotifyTrackService trackService;

    @GetMapping("/search")
    public ResponseEntity<Paging<Track>> searchTracks(
            @RequestParam String query,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(trackService.searchTracks(query, limit, offset));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrack(@PathVariable String id) {
        return ResponseEntity.ok(trackService.getTrack(id));
    }

    @GetMapping
    public ResponseEntity<Track[]> getTracks(@RequestParam String[] ids) {
        return ResponseEntity.ok(trackService.getTracks(ids));
    }
} 