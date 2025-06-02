package org.eduardomango.clasespringsecurity.security.controllers;

import org.eduardomango.clasespringsecurity.security.dto.AuthRequest;
import org.eduardomango.clasespringsecurity.security.dto.AuthResponse;
import org.eduardomango.clasespringsecurity.security.dto.RefreshTokenRequest;
import org.eduardomango.clasespringsecurity.security.entities.CredentialsEntity;
import org.eduardomango.clasespringsecurity.security.services.AuthService;
import org.eduardomango.clasespringsecurity.security.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping()
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest){
        CredentialsEntity user = authService.authenticate(authRequest);
        System.out.println(user);
        String token = jwtService.generateToken(user);

        System.out.println(token);
        return ResponseEntity.ok(new AuthResponse(token, user.getRefreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request){

        AuthResponse response = authService.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }
}
