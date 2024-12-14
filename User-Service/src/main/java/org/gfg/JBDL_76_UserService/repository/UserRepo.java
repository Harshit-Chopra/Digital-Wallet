package org.gfg.JBDL_76_UserService.repository;

import org.gfg.JBDL_76_UserService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
}
