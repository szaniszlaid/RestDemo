package hu.szaniszlaid.webdemo.domain;

import static hu.szaniszlaid.webdemo.domain.GeneratorUtils.generateRandomString;

public class UserGenerator {

    /**
     * creates a sample user
     *
     * @return a new random {@link User}
     */
    public static User genearateUser() {
        User testUser = new User();
        testUser.setUsername(generateRandomString(100));
        testUser.setName(generateRandomString(200));
        testUser.setPassword(generateRandomString(100));
        testUser.setEmail(generateRandomString(50) + "@" + generateRandomString(50));

        return testUser;
    }
}
