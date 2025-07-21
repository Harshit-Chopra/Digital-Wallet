package org.gfg.UserService.repository;

import org.gfg.UserService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
}
