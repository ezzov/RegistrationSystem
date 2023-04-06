package com.example.registrationsystem.security;

import com.example.registrationsystem.models.Role;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User details service implementation that is necessary for security
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Method that get user details by user email
     * @param email from {@link User}
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException if user is not found in database
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Request for user by email from database");
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        log.info("Response with new User from userdetails package");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), rolesToAuthorities(user.getRoles()));
    }

    /**
     * Method that converts set of roles to collection of granted authority
     * @param roles {@link Set} of {@link Role}
     * @return {@link Collection} of {@link GrantedAuthority}
     */
    private Collection<GrantedAuthority> rolesToAuthorities(Set<Role> roles){
        log.info("Return the collection of GrantedAuthority mapped from roles");
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }


    /**
     * Method tht loads user email by user id
     * @param userId from {@link User}
     * @return email from {@link User}
     */
    public String loadEmailByUserId(long userId) {
        log.info("Request for user with userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id = " + userId));
        log.info("Return user email");
        return user.getEmail();
    }
}
