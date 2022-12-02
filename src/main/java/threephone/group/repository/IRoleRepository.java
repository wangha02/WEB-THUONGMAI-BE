package threephone.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threephone.group.model.Role;
import threephone.group.model.RoleName;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
