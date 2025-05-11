package com.musicfy.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyTrackService {

    private final SpotifyApi spotifyApi;

    public Paging<Track> searchTracks(String query, int limit, int offset) {
        try {
            SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query)
                    .limit(limit)
                    .offset(offset)
                    .build();

            return searchTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error searching tracks: ", e);
            throw new RuntimeException("Failed to search tracks", e);
        }
    }

    public Track getTrack(String trackId) {
        try {
            return spotifyApi.getTrack(trackId).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting track: ", e);
            throw new RuntimeException("Failed to get track", e);
        }
    }

    public Track[] getTracks(String... trackIds) {
        try {
            return spotifyApi.getSeveralTracks(trackIds).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting tracks: ", e);
            throw new RuntimeException("Failed to get tracks", e);
        }
    }
} 