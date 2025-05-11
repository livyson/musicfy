package com.musicfy.config;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.List;

@Configuration
@Getter
public class SpotifyConfig {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.client.redirect-uri}")
    private String redirectUri;

    @Value("${spotify.scopes}")
    private List<String> scopes;

    @Bean
    public SpotifyApi spotifyApi() {
        return SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(SpotifyHttpManager.makeUri(redirectUri))
                .build();
    }
} 