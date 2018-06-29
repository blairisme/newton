package org.ucl.newton.service.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ucl.newton.framework.Credential;

import javax.inject.Inject;

public class AuthenticationService implements UserDetailsService
{
    private CredentialRepository repository;

    @Inject
    public AuthenticationService(CredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential credential = repository.getCredential(username);
        if (credential == null) {
            throw new UsernameNotFoundException(username);
        }
        return credential;
    }
}
