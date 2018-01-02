package hu.szaniszlaid.webdemo.repository;


import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.domain.UserGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Integration test class for User repository
*/
//@RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit. Whenever we are using any Spring Boot testing features in out JUnit tests, this annotation will be required.
//@DataJpaTest provides some standard setup needed for testing the persistence layer:
//        configuring H2, an in-memory database
//        setting Hibernate, Spring Data, and the DataSource
//        performing an @EntityScan
//        turning on SQL logging
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void findOneTest() {
        User userToSave = UserGenerator.generateUser();

        User retrievedUser = repository.save(userToSave);

        assertThat(repository.findById(retrievedUser.getId()), is(not(nullValue())));
        assertThat(repository.findById(retrievedUser.getId() + 1), is(Optional.empty()));

        assertThat(retrievedUser, samePropertyValuesAs(userToSave));
    }
}
