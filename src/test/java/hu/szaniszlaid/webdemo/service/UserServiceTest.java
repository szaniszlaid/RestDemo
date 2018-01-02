package hu.szaniszlaid.webdemo.service;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.domain.UserGenerator;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

/**
 * Now these test cases are totally unnecessary, these are written just for practice.
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    UserRepository userRepository;


    @Test
    public void getExistingUserById() {
        User mockedUser = UserGenerator.generateUser();
        mockedUser.setId(1L);

        Mockito.when(userRepository.findById(mockedUser.getId())).thenReturn(Optional.ofNullable(mockedUser));

        assertThat(service.getUserById(mockedUser.getId()), is(Optional.of(mockedUser)));
        assertThat(service.getUserById(mockedUser.getId()).get(), samePropertyValuesAs(mockedUser));
    }

    @Test
    public void getNonExistingUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThat(service.getUserById(1L), is(Optional.empty()));
    }

    @Test
    public void saveNewValidUser() {
        User userToSave = UserGenerator.generateUser();
        Mockito.when(userRepository.save(userToSave)).thenReturn(userToSave);

        User retrievedUser = userRepository.save(userToSave);
        assertThat(retrievedUser, samePropertyValuesAs(userToSave));
    }

    @Test
    public void updateUser() {
        User user = UserGenerator.generateUser();

        Mockito.when(userRepository.save(user)).thenReturn(user);

        User userToUpdate = service.save(user);

        userToUpdate.setName("new name to update");

        Mockito.when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        User updatedUser = service.save(userToUpdate);

        assertThat(updatedUser, samePropertyValuesAs(userToUpdate));

    }
}
