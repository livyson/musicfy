package com.musicfy.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumsTracksRequest;
import com.wrapper.spotify.requests.data.albums.GetSeveralAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyAlbumService {

    private final SpotifyApi spotifyApi;

    public Album getAlbum(String albumId) {
        try {
            GetAlbumRequest request = spotifyApi.getAlbum(albumId).build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting album: ", e);
            throw new RuntimeException("Failed to get album", e);
        }
    }

    public Album[] getAlbums(String... albumIds) {
        try {
            GetSeveralAlbumsRequest request = spotifyApi.getSeveralAlbums(albumIds).build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting albums: ", e);
            throw new RuntimeException("Failed to get albums", e);
        }
    }

    public Paging<AlbumSimplified> searchAlbums(String query, int limit, int offset) {
        try {
            SearchAlbumsRequest request = spotifyApi.searchAlbums(query)
                    .limit(limit)
                    .offset(offset)
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error searching albums: ", e);
            throw new RuntimeException("Failed to search albums", e);
        }
    }

    public Paging<TrackSimplified> getAlbumTracks(String albumId, int limit, int offset) {
        try {
            GetAlbumsTracksRequest request = spotifyApi.getAlbumsTracks(albumId)
                    .limit(limit)
                    .offset(offset)
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting album tracks: ", e);
            throw new RuntimeException("Failed to get album tracks", e);
        }
    }

    public Album[] getNewReleases(String country, int limit, int offset) {
        try {
            return spotifyApi.getNewReleases()
                    .country(country)
                    .limit(limit)
                    .offset(offset)
                    .build()
                    .execute()
                    .getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting new releases: ", e);
            throw new RuntimeException("Failed to get new releases", e);
        }
    }
} 