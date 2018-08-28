/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ucl.newton.service.authentication.AuthenticationService;

import javax.inject.Inject;

/**
 * Instances of this class configure which endpoints require authentication and
 * which do not.
 *
 * @author Blair Butterworth
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Inject
    protected AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setAuthorizedPaths(http);
        setAuthenticationMethods(http);
        setCrossOrigin(http);
        setHeaderPolicy(http);
    }

    private void setAuthorizedPaths(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/signup", "/resources/**", "/.well-known/**").permitAll()
                .antMatchers("/settings/data-permissions").hasAnyRole("ADMIN", "ORGANISATIONLEAD")
                .antMatchers("/settings/**").hasAnyRole("ADMIN")
                .antMatchers("/project/new").hasAnyRole("ADMIN", "ORGANISATIONLEAD")
                .antMatchers(HttpMethod.POST, "/api/**").hasAnyRole("API", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("API", "ADMIN")
                .anyRequest().authenticated();
    }

    private void setAuthenticationMethods(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/projects")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access_denied");
    }

    private void setCrossOrigin(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    private void setHeaderPolicy(HttpSecurity http) throws Exception {
        http.headers()
            .frameOptions()
                .sameOrigin()
            .httpStrictTransportSecurity()
                .disable();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return authenticationService;
    }
}