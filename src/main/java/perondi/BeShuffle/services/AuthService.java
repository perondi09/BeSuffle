package perondi.BeShuffle.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import perondi.BeShuffle.client.AuthSpotifyClient;
import perondi.BeShuffle.dtos.login.LoginRequest;
import perondi.BeShuffle.dtos.login.LoginResponse;

@Service
public class AuthService {

    private final AuthSpotifyClient authSpotifyClient;

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public AuthService(AuthSpotifyClient authSpotifyClient) {
        this.authSpotifyClient = authSpotifyClient;
    }

    public String getAccessToken() {
        var loginRequest = new LoginRequest("client_credentials", clientId, clientSecret);
        LoginResponse response = authSpotifyClient.login(loginRequest);
        return response.getAccessToken();
    }
}
