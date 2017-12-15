package hu.szaniszlaid.webdemo.service;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.domain.UserGenerator;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    UserRepository userRepository;



    @Before
    public void setup() {

    }

    @Test
    public void getExistingUserById() {
        User userToSave = new User();

        userToSave.setId(1L);
        userToSave.setUsername("test_username_01");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userToSave));
        assertThat(service.getUserById(userToSave.getId()), is(Optional.of(userToSave)));
    }

    @Test
    public void getNonExistentUser() {
        assertThat(service.getUserById(2L), is(Optional.empty()));
    }

    @Test
    public void saveNewValidUser() {
        User userToSave = service.save(UserGenerator.generateUser());

        assertThat(userToSave.getId(), is(notNullValue()));

        User retrievedUser = service.getUserById(userToSave.getId()).orElse(null);
        assertThat(retrievedUser, samePropertyValuesAs(userToSave));
    }

    @Test
    public void updateUser() {
        User originalUser = service.save(UserGenerator.generateUser());

        User userToUpdate = UserGenerator.generateUser();
        userToUpdate.setId(originalUser.getId());

        service.save(userToUpdate);

        User retrievedUser = service.getUserById(originalUser.getId()).orElse(null);

        assertThat(retrievedUser, samePropertyValuesAs(userToUpdate));

    }
}
