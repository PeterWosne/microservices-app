package ru.ermakow.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ermakow.market.auth.entities.Role;
import ru.ermakow.market.auth.repositories.RoleRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    //метод достать роль юзера, пригодится при создании нового юзера
    public Optional<Role> getUserRole() {
        return roleRepository.findByTitle("ROLE_USER");
    }
}
