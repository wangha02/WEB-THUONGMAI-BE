package threephone.group.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import threephone.group.model.User;
import threephone.group.repository.IUserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional

public class UserServiceIMPL implements IUserService{
    @Autowired
    private IUserRepository userRepository;
    @Override
    public Boolean existsByUsername(String user) {
        return userRepository.existsByUsername(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Long isUserPresent(User user){
        User user1 = userRepository.getUserByEmailAndName(user.getName(), user.getEmail());
        return user1!=null ? user1.getId(): null ;
    }
}
