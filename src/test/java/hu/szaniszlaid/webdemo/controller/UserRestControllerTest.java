package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.DemoApplication;
import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import hu.szaniszlaid.webdemo.utils.MatcherUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Random;

import static hu.szaniszlaid.webdemo.controller.BaseController.API_URL;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class UserRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void UserGET() throws Exception {
        User testUser = userRepository.save(createTestUser());

        mockMvc.perform(get(API_URL + "user").param("id", testUser.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", MatcherUtils.isLongEqual(testUser.getId())))
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.name", is(testUser.getName())))
                .andExpect(jsonPath("$.email", is(testUser.getEmail())))
                .andExpect(jsonPath("$.password", is(testUser.getPassword())));
    }

    @Test
    public void UserPOST() {
        fail("Not yet implemented");
    }


    /**
     * creates a sample user
     * @return a random {@link User}
     * */
    private User createTestUser() {
        User testUser = new User();
        testUser.setUsername("alex_username " + new Random().nextInt(99999));
        testUser.setEmail("testusermail@mail.com");
        testUser.setName("Sample utf-8 User őűáúóüö");
        testUser.setPassword("úpoiúüö182389578139ö246rsdfhsdjklfh sdíkf yfjí-lkí-yfjáasjfőiufa8e9f7üafúsadf5498456");

        return testUser;
    }

}
