package com.musicfy.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpotifyTrackServiceTest {

    @Mock
    private SpotifyApi spotifyApi;

    @Mock
    private SearchTracksRequest.Builder searchTracksRequestBuilder;

    @Mock
    private SearchTracksRequest searchTracksRequest;

    @InjectMocks
    private SpotifyTrackService trackService;

    @BeforeEach
    void setUp() {
        when(spotifyApi.searchTracks(any())).thenReturn(searchTracksRequestBuilder);
        when(searchTracksRequestBuilder.limit(anyInt())).thenReturn(searchTracksRequestBuilder);
        when(searchTracksRequestBuilder.offset(anyInt())).thenReturn(searchTracksRequestBuilder);
        when(searchTracksRequestBuilder.build()).thenReturn(searchTracksRequest);
    }

    @Test
    void searchTracks_ShouldReturnTracks() throws Exception {
        // Arrange
        String query = "test query";
        int limit = 20;
        int offset = 0;
        Track[] expectedTracks = new Track[0];
        when(searchTracksRequest.execute()).thenReturn(expectedTracks);

        // Act
        Track[] actualTracks = trackService.searchTracks(query, limit, offset);

        // Assert
        assertNotNull(actualTracks);
        assertEquals(expectedTracks, actualTracks);
        verify(spotifyApi).searchTracks(query);
        verify(searchTracksRequestBuilder).limit(limit);
        verify(searchTracksRequestBuilder).offset(offset);
        verify(searchTracksRequest).execute();
    }

    @Test
    void searchTracks_WhenExceptionOccurs_ShouldThrowRuntimeException() throws Exception {
        // Arrange
        String query = "test query";
        when(searchTracksRequest.execute()).thenThrow(new RuntimeException("API Error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> trackService.searchTracks(query, 20, 0));
    }
} 