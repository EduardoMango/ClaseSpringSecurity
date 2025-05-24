package org.eduardomango.clasespringsecurity.security.services;

import org.eduardomango.clasespringsecurity.security.dto.AuthRequest;
import org.eduardomango.clasespringsecurity.security.entities.CredentialsEntity;
import org.eduardomango.clasespringsecurity.security.repositories.CredentialsRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CredentialsRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(CredentialsRepository credentialsRepository, AuthenticationManager authenticationManager) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
    }

    public UserDetails authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return credentialsRepository.findByEmail(input.username()).orElseThrow();
    }
}
