package com.musicfy.controller;

import com.musicfy.service.SpotifyPlaylistService;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final SpotifyPlaylistService playlistService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Paging<PlaylistSimplified>> getUserPlaylists(
            @PathVariable String userId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(playlistService.getUserPlaylists(userId, limit, offset));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable String id) {
        return ResponseEntity.ok(playlistService.getPlaylist(id));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Playlist> createPlaylist(
            @PathVariable String userId,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "true") boolean isPublic) {
        return ResponseEntity.ok(playlistService.createPlaylist(userId, name, description, isPublic));
    }

    @PostMapping("/{playlistId}/tracks")
    public ResponseEntity<Void> addTracksToPlaylist(
            @PathVariable String playlistId,
            @RequestParam String[] trackUris) {
        playlistService.addTracksToPlaylist(playlistId, trackUris);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playlistId}/tracks")
    public ResponseEntity<Void> removeTracksFromPlaylist(
            @PathVariable String playlistId,
            @RequestParam String[] trackUris) {
        playlistService.removeTracksFromPlaylist(playlistId, trackUris);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<Paging<PlaylistTrack>> getPlaylistTracks(
            @PathVariable String playlistId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(playlistService.getPlaylistTracks(playlistId, limit, offset));
    }
} 