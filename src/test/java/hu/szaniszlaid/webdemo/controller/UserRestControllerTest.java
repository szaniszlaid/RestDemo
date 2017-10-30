package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static hu.szaniszlaid.webdemo.controller.BaseController.API_URL;
import static hu.szaniszlaid.webdemo.domain.UserGenerator.generateUser;
import static hu.szaniszlaid.webdemo.utils.MatcherUtils.isLongEqual;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


public class UserRestControllerTest extends BaseControllerTest {

    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
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

    /**
     * GET user by "id" param
     * */
    @Test
    public void findUser() throws Exception {
        User testUser = userRepository.save(generateUser());

        mockMvc.perform(get(API_URL + "user").param("id", testUser.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", isLongEqual(testUser.getId())))
                .andExpect(jsonPath("$.username", is(testUser.getUsername())))
                .andExpect(jsonPath("$.name", is(testUser.getName())))
                .andExpect(jsonPath("$.email", is(testUser.getEmail())))
                .andExpect(jsonPath("$.password", is(testUser.getPassword())));
    }

    @Test
    public void UserNotFound() throws Exception {
        mockMvc.perform(get(API_URL + "user").param("id", "9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void UserPOST() {
        fail("Not yet implemented");
    }

    /**
     * POST new user
     * */
    @Test
    public void createUser() throws Exception {
        User userToPost = generateUser();
        String userJson = json(userToPost);

        mockMvc.perform(post(API_URL + "user")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.username", is(userToPost.getUsername())))
                .andExpect(jsonPath("$.name", is(userToPost.getName())))
                .andExpect(jsonPath("$.email", is(userToPost.getEmail())))
                .andExpect(jsonPath("$.password", is(userToPost.getPassword())));
    }


}
