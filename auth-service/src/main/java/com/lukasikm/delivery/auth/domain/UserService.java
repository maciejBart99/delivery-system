package com.lukasikm.delivery.auth.domain;

import com.lukasikm.delivery.auth.domain.entity.User;
import com.lukasikm.delivery.authserviceclient.dto.UserRole;
import com.lukasikm.delivery.auth.domain.exception.CredentialsIncorrectException;
import com.lukasikm.delivery.auth.domain.exception.UserNotFoundException;
import com.lukasikm.delivery.auth.domain.repository.UserRepository;
import com.lukasikm.delivery.authserviceclient.dto.CredentialsDTO;
import com.lukasikm.delivery.authserviceclient.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(User.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(User::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDTO loginOrRegister(CredentialsDTO credentials) {
        return userRepository.getUseByEmail(credentials.getEmail())
                .map(user -> tryFindExisting(credentials, user))
                .orElseGet(() -> tryCreateAccount(credentials));
    }

    private UserDTO tryFindExisting(CredentialsDTO credentials, User user) {
        if (passwordEncoder.matches(credentials.getPassword(), user.getPasswordHash())) {
            return user.toDto();
        }

        logger.info("Invalid password used for user with id={}", user.getId());
        throw new CredentialsIncorrectException();
    }

    private UserDTO tryCreateAccount(CredentialsDTO credentials) {
        var user = new User(UUID.randomUUID(), credentials.getEmail(),
                passwordEncoder.encode(credentials.getPassword()), UserRole.Client);
        userRepository.save(user);
        logger.info("Account created for user with email={}", credentials.getEmail());

        return user.toDto();
    }
}
