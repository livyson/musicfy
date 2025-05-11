package com.musicfy.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyRecommendationService {

    private final SpotifyApi spotifyApi;

    public Recommendations getRecommendations(
            List<String> seedArtists,
            List<String> seedGenres,
            List<String> seedTracks,
            int limit,
            String market,
            Float minPopularity,
            Float maxPopularity,
            Float targetPopularity,
            Float minEnergy,
            Float maxEnergy,
            Float targetEnergy,
            Float minDanceability,
            Float maxDanceability,
            Float targetDanceability) {
        try {
            GetRecommendationsRequest.Builder requestBuilder = spotifyApi.getRecommendations()
                    .limit(limit)
                    .market(market);

            // Add seeds (up to 5 total)
            if (seedArtists != null && !seedArtists.isEmpty()) {
                requestBuilder.seed_artists(seedArtists.stream()
                        .limit(5 - (seedGenres != null ? seedGenres.size() : 0) - (seedTracks != null ? seedTracks.size() : 0))
                        .collect(Collectors.joining(",")));
            }
            if (seedGenres != null && !seedGenres.isEmpty()) {
                requestBuilder.seed_genres(seedGenres.stream()
                        .limit(5 - (seedArtists != null ? seedArtists.size() : 0) - (seedTracks != null ? seedTracks.size() : 0))
                        .collect(Collectors.joining(",")));
            }
            if (seedTracks != null && !seedTracks.isEmpty()) {
                requestBuilder.seed_tracks(seedTracks.stream()
                        .limit(5 - (seedArtists != null ? seedArtists.size() : 0) - (seedGenres != null ? seedGenres.size() : 0))
                        .collect(Collectors.joining(",")));
            }

            // Add audio features
            if (minPopularity != null) requestBuilder.min_popularity(minPopularity.intValue());
            if (maxPopularity != null) requestBuilder.max_popularity(maxPopularity.intValue());
            if (targetPopularity != null) requestBuilder.target_popularity(targetPopularity.intValue());
            if (minEnergy != null) requestBuilder.min_energy(minEnergy);
            if (maxEnergy != null) requestBuilder.max_energy(maxEnergy);
            if (targetEnergy != null) requestBuilder.target_energy(targetEnergy);
            if (minDanceability != null) requestBuilder.min_danceability(minDanceability);
            if (maxDanceability != null) requestBuilder.max_danceability(maxDanceability);
            if (targetDanceability != null) requestBuilder.target_danceability(targetDanceability);

            return requestBuilder.build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting recommendations: ", e);
            throw new RuntimeException("Failed to get recommendations", e);
        }
    }

    public Track[] getRecommendationsForTrack(String trackId, int limit, String market) {
        try {
            Recommendations recommendations = spotifyApi.getRecommendations()
                    .seed_tracks(trackId)
                    .limit(limit)
                    .market(market)
                    .build()
                    .execute();
            return recommendations.getTracks();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting track recommendations: ", e);
            throw new RuntimeException("Failed to get track recommendations", e);
        }
    }

    public Track[] getRecommendationsForArtist(String artistId, int limit, String market) {
        try {
            Recommendations recommendations = spotifyApi.getRecommendations()
                    .seed_artists(artistId)
                    .limit(limit)
                    .market(market)
                    .build()
                    .execute();
            return recommendations.getTracks();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("Error getting artist recommendations: ", e);
            throw new RuntimeException("Failed to get artist recommendations", e);
        }
    }
} 