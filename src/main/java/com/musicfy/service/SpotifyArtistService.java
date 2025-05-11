package com.musicfy.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import com.wrapper.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import com.wrapper.spotify.requests.data.artists.GetSeveralArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyArtistService {

    private final SpotifyApi spotifyApi;

    public Artist getArtist(String artistId) {
        try {
            GetArtistRequest request = spotifyApi.getArtist(artistId).build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting artist: ", e);
            throw new RuntimeException("Failed to get artist", e);
        }
    }

    public Artist[] getArtists(String... artistIds) {
        try {
            GetSeveralArtistsRequest request = spotifyApi.getSeveralArtists(artistIds).build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting artists: ", e);
            throw new RuntimeException("Failed to get artists", e);
        }
    }

    public Paging<Artist> searchArtists(String query, int limit, int offset) {
        try {
            SearchArtistsRequest request = spotifyApi.searchArtists(query)
                    .limit(limit)
                    .offset(offset)
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error searching artists: ", e);
            throw new RuntimeException("Failed to search artists", e);
        }
    }

    public Paging<AlbumSimplified> getArtistAlbums(String artistId, int limit, int offset) {
        try {
            GetArtistsAlbumsRequest request = spotifyApi.getArtistsAlbums(artistId)
                    .limit(limit)
                    .offset(offset)
                    .album_type(GetArtistsAlbumsRequest.AlbumType.ALBUM)
                    .build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting artist albums: ", e);
            throw new RuntimeException("Failed to get artist albums", e);
        }
    }

    public Track[] getArtistTopTracks(String artistId, String market) {
        try {
            GetArtistsTopTracksRequest request = spotifyApi.getArtistsTopTracks(artistId, market).build();
            return request.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting artist top tracks: ", e);
            throw new RuntimeException("Failed to get artist top tracks", e);
        }
    }

    public Artist[] getRelatedArtists(String artistId) {
        try {
            return spotifyApi.getArtistsRelatedArtists(artistId).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting related artists: ", e);
            throw new RuntimeException("Failed to get related artists", e);
        }
    }
} 