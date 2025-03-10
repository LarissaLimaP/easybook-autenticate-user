package com.puc.easybookautenticateuser.repository;

import com.puc.easybookautenticateuser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>  {
    boolean existsByUsername(String username);
}
