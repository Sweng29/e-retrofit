package com.retrofit.app.repository;

import com.retrofit.app.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNo);

    @Query("FROM User u WHERE u.username =:parameter OR u.email =:parameter OR u.mobileNumber=:parameter AND u.isActive = TRUE")
    Optional<User> findByUsernameOrEmailOrMobileNumber(@Param("parameter") String username);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobileNumber(String mobileNumber);

}
