package ru.ermakow.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ermakow.market.api.RegisterUserDto;
import ru.ermakow.market.auth.entities.Role;
import ru.ermakow.market.auth.entities.User;
import ru.ermakow.market.auth.exceptions.ResourceNotFoundException;
import ru.ermakow.market.auth.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService { //для работы со Spring Security юзерсервис должен реализовывать UserDetailsService

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    } //преобразовываем User к UserDetails

    //маппим коллекцию ролей к коллекции SimpleGrantedAuthorities
    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getTitle()))
                .collect(Collectors.toList());
    }

    //создание нового юзера
    public void createNewUser(RegisterUserDto registerUserDto) {
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleService.getUserRole().get());

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        userRepository.save(user);
    }
}
