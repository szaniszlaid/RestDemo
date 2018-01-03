package hu.szaniszlaid.webdemo.service;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User save(User user) {
        //TODO log new user
        return userRepository.save(user);
    }
}
