package com.amuryllis.teamflow_backend.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

  private final AppUserRepository repository;

  public AppUserDetailsService(AppUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser =
        repository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return User.builder()
        .username(appUser.getUsername())
        .password(appUser.getPasswordHash())
        .roles("ADMIN")
        .build();
  }
}
