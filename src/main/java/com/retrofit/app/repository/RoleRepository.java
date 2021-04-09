package com.retrofit.app.repository;

import com.retrofit.app.constants.RoleConstant;
import com.retrofit.app.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleConstant(RoleConstant roleConstant);
}