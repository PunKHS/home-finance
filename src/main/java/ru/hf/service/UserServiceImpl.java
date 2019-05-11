package ru.hf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hf.model.Role;
import ru.hf.model.Status;
import ru.hf.model.User;
import ru.hf.repository.RoleRepository;
import ru.hf.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRole = new ArrayList<>();
        userRole.add(roleUser);

        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRole);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);
        log.info("UserService register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("UserService getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User getByUsername(String username) {
        User result = userRepository.findByUsername(username.toLowerCase());
        log.info("UserService getByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User getById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("UserService getById - no user found by id: {}", id);
            return null;
        }
        log.info("UserService getById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("UserService delete - user with id: {} successfully deleted", id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());
        log.info("UserService getByUsername - user: {} found by username: {}", user, username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }
}
