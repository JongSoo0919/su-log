package com.sulog.api.repository.user;

import com.sulog.api.domain.user.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByEmailAndPassword(String email, String password);
}
