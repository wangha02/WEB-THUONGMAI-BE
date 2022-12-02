package threephone.group.service.role;

import threephone.group.model.Role;
import threephone.group.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleName);
}
