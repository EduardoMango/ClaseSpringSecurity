package org.eduardomango.clasespringsecurity.security.services;

import org.eduardomango.clasespringsecurity.security.repositories.CredentialsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final CredentialsRepository credentialsRepository;

    public UserDetailsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsRepository.findByEmail(username).orElseThrow();
    }
}
