package com.musicfy.controller;

import com.musicfy.service.SpotifyArtistService;
import com.wrapper.spotify.model_objects.specification.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final SpotifyArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable String id) {
        return ResponseEntity.ok(artistService.getArtist(id));
    }

    @GetMapping
    public ResponseEntity<Artist[]> getArtists(@RequestParam String[] ids) {
        return ResponseEntity.ok(artistService.getArtists(ids));
    }

    @GetMapping("/search")
    public ResponseEntity<Paging<Artist>> searchArtists(
            @RequestParam String query,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(artistService.searchArtists(query, limit, offset));
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<Paging<AlbumSimplified>> getArtistAlbums(
            @PathVariable String id,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(artistService.getArtistAlbums(id, limit, offset));
    }

    @GetMapping("/{id}/top-tracks")
    public ResponseEntity<Track[]> getArtistTopTracks(
            @PathVariable String id,
            @RequestParam(defaultValue = "US") String market) {
        return ResponseEntity.ok(artistService.getArtistTopTracks(id, market));
    }

    @GetMapping("/{id}/related")
    public ResponseEntity<Artist[]> getRelatedArtists(@PathVariable String id) {
        return ResponseEntity.ok(artistService.getRelatedArtists(id));
    }
} 