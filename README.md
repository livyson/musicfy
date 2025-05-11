# Musicfy

A Spring Boot application that integrates with Spotify's Web API to provide a rich set of music-related features.

## Features

- Spotify OAuth2 authentication
- Track search and retrieval
- User profile information
- Playlist management
- Artist and album browsing
- Music recommendations
- Top tracks and artists

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Spotify Developer Account
- Spotify Client ID and Client Secret

## Setup

1. Create a Spotify Developer account at https://developer.spotify.com/dashboard
2. Create a new application in the Spotify Developer Dashboard
3. Get your Client ID and Client Secret
4. Add `http://localhost:8080/api/auth/callback` as a Redirect URI in your Spotify application settings

## Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
SPOTIFY_CLIENT_ID=your_client_id_here
SPOTIFY_CLIENT_SECRET=your_client_secret_here
```

## Building and Running

1. Clone the repository:
```bash
git clone https://github.com/yourusername/musicfy.git
cd musicfy
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## API Endpoints

### Authentication

- `GET /api/auth/login` - Initiate Spotify login
- `GET /api/auth/callback` - Spotify OAuth callback
- `POST /api/auth/refresh` - Refresh access token

### Tracks

- `GET /api/tracks/search?query={query}&limit={limit}&offset={offset}` - Search for tracks
- `GET /api/tracks/{id}` - Get track by ID
- `GET /api/tracks?ids={ids}` - Get multiple tracks by IDs

### Playlists

- `GET /api/playlists/user/{userId}` - Get user's playlists
- `GET /api/playlists/{id}` - Get playlist by ID
- `POST /api/playlists/user/{userId}` - Create a new playlist
- `POST /api/playlists/{playlistId}/tracks` - Add tracks to playlist
- `DELETE /api/playlists/{playlistId}/tracks` - Remove tracks from playlist
- `GET /api/playlists/{playlistId}/tracks` - Get playlist tracks

### Users

- `GET /api/users/me` - Get current user's profile
- `GET /api/users/{id}` - Get user profile by ID
- `GET /api/users/me/top/tracks` - Get user's top tracks
- `GET /api/users/me/top/artists` - Get user's top artists

### Artists

- `GET /api/artists/{id}` - Get artist by ID
- `GET /api/artists?ids={ids}` - Get multiple artists by IDs
- `GET /api/artists/search?query={query}&limit={limit}&offset={offset}` - Search for artists
- `GET /api/artists/{id}/albums` - Get artist's albums
- `GET /api/artists/{id}/top-tracks` - Get artist's top tracks
- `GET /api/artists/{id}/related` - Get related artists

### Albums

- `GET /api/albums/{id}` - Get album by ID
- `GET /api/albums?ids={ids}` - Get multiple albums by IDs
- `GET /api/albums/search?query={query}&limit={limit}&offset={offset}` - Search for albums
- `GET /api/albums/{id}/tracks` - Get album tracks
- `GET /api/albums/new-releases` - Get new releases

### Recommendations

- `GET /api/recommendations` - Get personalized recommendations
  - Query parameters:
    - `seedArtists` - List of artist IDs
    - `seedGenres` - List of genres
    - `seedTracks` - List of track IDs
    - `limit` - Number of recommendations (default: 20)
    - `market` - Market code (default: US)
    - Audio features (optional):
      - `minPopularity`, `maxPopularity`, `targetPopularity`
      - `minEnergy`, `maxEnergy`, `targetEnergy`
      - `minDanceability`, `maxDanceability`, `targetDanceability`
- `GET /api/recommendations/track/{trackId}` - Get recommendations based on a track
- `GET /api/recommendations/artist/{artistId}` - Get recommendations based on an artist

## Security

The application uses Spring Security and Spotify's OAuth2 implementation for secure authentication. All API endpoints (except login) require a valid Spotify access token.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
