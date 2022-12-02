package threephone.group.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import threephone.group.model.Role;
import threephone.group.model.RoleName;
import threephone.group.repository.IRoleRepository;

import java.util.Optional;

@Service
public class RoleServiceIMPL implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}
