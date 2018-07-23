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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;
import org.ucl.newton.framework.UserDto;
import org.ucl.newton.framework.UserRole;

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

    private BCryptPasswordEncoder passwordEncoder;

    @Inject
    public AuthenticationService(CredentialRepository repository) {
        this.repository = repository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential credential = repository.getCredentialByName(username);
        if (credential == null) {
            throw new UsernameNotFoundException(username);
        }
        return credential;
    }

    public void save(UserDto userDto, User user) {
        Credential userCredentials = new Credential(user.getId(), user.getEmail(),
                passwordEncoder.encode(userDto.getPassword()), UserRole.USER);
        repository.addCredential(userCredentials);
    }
}
