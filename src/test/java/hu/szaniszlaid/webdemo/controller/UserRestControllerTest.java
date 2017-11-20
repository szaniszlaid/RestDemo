package hu.szaniszlaid.webdemo.controller;

import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJsonTesters
public class UserRestControllerTest {

    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * AutoConfigureJsonTesters annotation initializes this field
     */
    @SuppressWarnings("unused")
    private JacksonTester<User> jsonTester;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    /**
     * GET user by "id" param
     */
    @Test
    public void findUser() throws Exception {
        User testUser = userService.save(generateUser());

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
        mockMvc.perform(get(API_URL + "user").param("id", "1"))
                .andExpect(status().isNoContent());
    }

    @Ignore
    @Test
    public void UserPOST() {
        fail("Not yet implemented");
    }

    /**
     * POST new user
     */
    @Test
    public void createUser() throws Exception {
        User userToPost = generateUser();
        String userJson = jsonTester.write(userToPost).getJson();

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
