package com.musicfy.service;

import com.musicfy.config.SpotifyConfig;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyAuthService {

    private final SpotifyConfig spotifyConfig;
    private final SpotifyApi spotifyApi;

    public URI getAuthorizationUri() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .scope(String.join(" ", spotifyConfig.getScopes()))
                .show_dialog(true)
                .build();

        return authorizationCodeUriRequest.execute();
    }

    public AuthorizationCodeCredentials getAccessToken(String code) {
        try {
            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                    .build();

            return authorizationCodeRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting access token: ", e);
            throw new RuntimeException("Failed to get access token", e);
        }
    }

    public AuthorizationCodeCredentials refreshAccessToken(String refreshToken) {
        try {
            spotifyApi.setRefreshToken(refreshToken);
            return spotifyApi.authorizationCodeRefresh().build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error refreshing access token: ", e);
            throw new RuntimeException("Failed to refresh access token", e);
        }
    }
} 