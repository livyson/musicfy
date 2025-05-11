package com.musicfy.controller;

import com.musicfy.service.SpotifyAuthService;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SpotifyAuthService spotifyAuthService;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        URI authorizationUri = spotifyAuthService.getAuthorizationUri();
        return ResponseEntity.status(302)
                .location(authorizationUri)
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthorizationCodeCredentials> callback(@RequestParam String code) {
        AuthorizationCodeCredentials credentials = spotifyAuthService.getAccessToken(code);
        return ResponseEntity.ok(credentials);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthorizationCodeCredentials> refreshToken(@RequestParam String refreshToken) {
        AuthorizationCodeCredentials credentials = spotifyAuthService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(credentials);
    }
} 