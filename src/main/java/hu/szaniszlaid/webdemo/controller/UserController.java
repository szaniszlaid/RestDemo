package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;


@RestController
public class UserController extends BaseController {

    private final UserService userService;

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

    @PostMapping("/user")
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
        validateEntityPost(user);

        User createdUser = userService.save(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri());
        return new ResponseEntity<>(createdUser, responseHeaders, HttpStatus.CREATED);
    }

}
