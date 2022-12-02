package threephone.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threephone.group.model.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String user);
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    User getUserByEmailAndName(String email,String name);
}
