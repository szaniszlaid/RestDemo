package hu.szaniszlaid.webdemo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.szaniszlaid.webdemo.domain.User;
import hu.szaniszlaid.webdemo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Optional;

import static hu.szaniszlaid.webdemo.controller.BaseController.API_URL;
import static hu.szaniszlaid.webdemo.domain.UserGenerator.generateUser;
import static hu.szaniszlaid.webdemo.utils.MatcherUtils.isLongEqual;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
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

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private GsonTester<User> gsonTester;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        Gson gson = new GsonBuilder().create();
        GsonTester.initFields(this, gson);
    }

    /**
     * GET user by "id" param
     */
    @Test
    public void findUser() throws Exception {
        User user = generateUser();
        user.setId(1L);

        Mockito.when(userService.getUserById(user.getId())).thenReturn(Optional.ofNullable(user));

        String userId = user.getId().toString();

        mockMvc.perform(get(API_URL + "user").param("id", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", isLongEqual(user.getId())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())));
    }

    @Test
    public void UserNotFound() throws Exception {
        mockMvc.perform(get(API_URL + "user").param("id", "1"))
                .andExpect(status().isNoContent());
    }


    /**
     * POST new user
     */
    @Test
    public void postValidUser() throws Exception {
        User userToPost = generateUser();
        String userJson = gsonTester.write(userToPost).getJson();

        User savedUser = userToPost;
        savedUser.setId(1L);

        Mockito.when(userService.save(any(User.class))).thenReturn(savedUser);

        String locationHeaderValue = BaseController.API_URL + "user/" + savedUser.getId().toString();


        mockMvc.perform(post(API_URL + "user")
                .contentType(contentType)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(userToPost.getUsername())))
                .andExpect(header().string("location", containsString(locationHeaderValue)));
    }


    /**
     * POST new user with id should return with HTTP.CONFLICT
     */

    @Test
    public void postUserWithId_isNotAcceptable() throws Exception {
        User userToPost = generateUser();
        userToPost.setId(1L);

        String userJson = gsonTester.write(userToPost).getJson();

        Mockito.when(userService.save(any(User.class))).thenReturn(userToPost);

        mockMvc.perform(post(API_URL + "user")
                .content(userJson)
                .contentType(contentType))
                .andExpect(jsonPath("$.title", is("Entity conflict")))
                .andExpect(jsonPath("$.detail", notNullValue()))
                .andExpect(status().isConflict());
    }


}
