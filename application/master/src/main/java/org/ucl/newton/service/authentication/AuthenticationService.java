/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ucl.newton.framework.Credential;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Instances of this class provide access to user credentials.
 *
 * @author Blair Butterworth
 */
@Named
public class AuthenticationService implements UserDetailsService
{
    private CredentialRepository repository;

    @Inject
    public AuthenticationService(CredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential credential = repository.getCredentialByName(username);
        if (credential == null) {
            throw new UsernameNotFoundException(username);
        }
        return credential;
    }
}
