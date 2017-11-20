package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class UserController extends BaseController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam("id") Long id) {
        Optional<User> findUser = userService.getUserById(id);

        if (findUser.isPresent()) {
            return ResponseEntity.ok(findUser.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
