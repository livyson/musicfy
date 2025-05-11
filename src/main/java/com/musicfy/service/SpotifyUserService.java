package com.musicfy.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import com.wrapper.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyUserService {

    private final SpotifyApi spotifyApi;

    public User getCurrentUser() {
        try {
            GetCurrentUsersProfileRequest request = spotifyApi.getCurrentUsersProfile()
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting current user: ", e);
            throw new RuntimeException("Failed to get current user", e);
        }
    }

    public Paging<Track> getTopTracks(int limit, int offset) {
        try {
            GetUsersTopTracksRequest request = spotifyApi.getUsersTopTracks()
                    .limit(limit)
                    .offset(offset)
                    .time_range(GetUsersTopTracksRequest.TimeRange.MEDIUM_TERM)
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting top tracks: ", e);
            throw new RuntimeException("Failed to get top tracks", e);
        }
    }

    public Paging<Artist> getTopArtists(int limit, int offset) {
        try {
            GetUsersTopArtistsRequest request = spotifyApi.getUsersTopArtists()
                    .limit(limit)
                    .offset(offset)
                    .time_range(GetUsersTopArtistsRequest.TimeRange.MEDIUM_TERM)
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting top artists: ", e);
            throw new RuntimeException("Failed to get top artists", e);
        }
    }

    public User getUser(String userId) {
        try {
            return spotifyApi.getUsersProfile(userId).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting user: ", e);
            throw new RuntimeException("Failed to get user", e);
        }
    }
} 