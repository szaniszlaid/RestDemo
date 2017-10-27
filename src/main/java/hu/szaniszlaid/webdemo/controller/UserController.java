package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UserController extends BaseController{

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public Optional<User> getUserById(@RequestParam("id") Long id) {
        return userRepository.findById(id);
    }

}
