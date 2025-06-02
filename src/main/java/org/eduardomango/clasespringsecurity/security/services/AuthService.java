package org.eduardomango.clasespringsecurity.security.services;

import org.eduardomango.clasespringsecurity.security.dto.AuthRequest;
import org.eduardomango.clasespringsecurity.security.dto.AuthResponse;
import org.eduardomango.clasespringsecurity.security.entities.CredentialsEntity;
import org.eduardomango.clasespringsecurity.security.repositories.CredentialsRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(CredentialsRepository credentialsRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Utiliza la clase AuthenticationManager para autenticar el usuario.
    public CredentialsEntity authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return credentialsRepository.findByEmail(input.username()).orElseThrow();
    }


    @Transactional
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        CredentialsEntity user = credentialsRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh token does not match");
        }

        if (!jwtService.validateRefreshToken(refreshToken, user)) {
            throw new IllegalArgumentException("Refresh token expired or invalid");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(newRefreshToken);
        credentialsRepository.save(user);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }


}
