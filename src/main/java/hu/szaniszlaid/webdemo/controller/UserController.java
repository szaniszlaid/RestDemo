package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class UserController extends BaseController{

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Optional<User> getUserById(@RequestParam("id") Long id) {
        return userRepository.findById(id);
    }

}
