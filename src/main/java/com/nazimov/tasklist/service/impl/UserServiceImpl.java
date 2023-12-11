package com.nazimov.tasklist.service.impl;

import com.nazimov.tasklist.domain.exception.ResourceNotFoundException;
import com.nazimov.tasklist.domain.user.Role;
import com.nazimov.tasklist.domain.user.User;
import com.nazimov.tasklist.repository.UserRepository;
import com.nazimov.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTaskOwner(Long taskId, Long userId) {
        return userRepository.isTaskOwner(taskId, userId);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new IllegalStateException("Password and password confirmation do not match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User existing = getById(user.getId());
        existing.setName(user.getName());
        existing.setUsername(user.getUsername());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
