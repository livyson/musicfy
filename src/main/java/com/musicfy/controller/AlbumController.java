package com.musicfy.controller;

import com.musicfy.service.SpotifyAlbumService;
import com.wrapper.spotify.model_objects.specification.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final SpotifyAlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable String id) {
        return ResponseEntity.ok(albumService.getAlbum(id));
    }

    @GetMapping
    public ResponseEntity<Album[]> getAlbums(@RequestParam String[] ids) {
        return ResponseEntity.ok(albumService.getAlbums(ids));
    }

    @GetMapping("/search")
    public ResponseEntity<Paging<AlbumSimplified>> searchAlbums(
            @RequestParam String query,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(albumService.searchAlbums(query, limit, offset));
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<Paging<TrackSimplified>> getAlbumTracks(
            @PathVariable String id,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(albumService.getAlbumTracks(id, limit, offset));
    }

    @GetMapping("/new-releases")
    public ResponseEntity<Album[]> getNewReleases(
            @RequestParam(defaultValue = "US") String country,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(albumService.getNewReleases(country, limit, offset));
    }
} 