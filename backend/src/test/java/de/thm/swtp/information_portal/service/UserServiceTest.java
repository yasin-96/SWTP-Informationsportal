package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.User.ResponseUser;
import de.thm.swtp.information_portal.models.User.User;
import de.thm.swtp.information_portal.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.net.URISyntaxException;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User userOne;
    private User userTwo;
    private User userThree;
    private User userNotExist;
    private User userWithInValidDataWithOutId;
    private User userWithInValidDataWithOutName;
    private User userWithInValidDataWithOutEmail;
    private User userWithInValidDataWithOutPrepUserName;
    private User userWithInValidDataAllNull;

    @BeforeEach
    public void Setup() {
        //clear all data in collection
        userRepository.deleteAll();

        //init new user
        userOne = new User("User1", "USER1", "user1@gmail.com", "usr1");
        userTwo = new User("User2", "USER2", "user2@gmail.com", "usr2");
        userThree = new User("User3", "USER3", "user3@gmail.com", "usr3");


        userNotExist = new User("User10", "USER10","user10@gmail.com", "usr10");
        userWithInValidDataWithOutId = new User("", "UserInva","UserInvalidID@gmail.com", "usrinvid");
        userWithInValidDataWithOutName = new User("UserInvalidName", "","UserInvalid@gmail.com", "usrinvname");
        userWithInValidDataWithOutEmail = new User("UserInvalidEmail", "UserInvEmail","", "usrinvemail");
        userWithInValidDataWithOutPrepUserName = new User("UserInvalidPrepName", "UserInvPrepName","UserInvalid@gmail.com", "");
        userWithInValidDataAllNull = new User("", "","", "");

        //save in database
        userRepository.save(userOne);
        userRepository.save(userTwo);
        userRepository.save(userThree);
    }

    @Test
    public void shouldFindUserByIdTest() {

        var users = userService.getUserById(userOne.getId());

        Assertions.assertNotNull(users);
        Assertions.assertEquals(userOne.getPreferred_username(), users.getBody().getUserName());
        Assertions.assertTrue(users.hasBody());
        Assertions.assertEquals(200, users.getStatusCodeValue());
    }

    @Test
    public void shouldFindLoggedInUserTest() throws URISyntaxException {

        var users = userService.getLoggedInUser(userOne.getId(), userOne);

        Assertions.assertNotNull(users);
        Assertions.assertEquals(userOne.getName(), users.getBody().getName());
        Assertions.assertTrue(users.hasBody());
        Assertions.assertEquals(200, users.getStatusCodeValue());
    }

    @Test
    public void shouldSaveUserIfNotExistsTest() {
        var responseEntity = userService.saveUserIfNotExists(userNotExist);

        Assertions.assertTrue(responseEntity.hasBody());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotSaveUserIfDataWasInvalid() {
        var responseEntityWithoutId = userService.saveUserIfNotExists(userWithInValidDataWithOutId);
        var responseEntityWithoutname = userService.saveUserIfNotExists(userWithInValidDataWithOutName);
        var responseEntityWithoutEmail = userService.saveUserIfNotExists(userWithInValidDataWithOutEmail);
        var responseEntityWithoutPrepName = userService.saveUserIfNotExists(userWithInValidDataWithOutPrepUserName);
        var responseEntityNull = userService.saveUserIfNotExists(userWithInValidDataAllNull);

        Assertions.assertNull(responseEntityWithoutId.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutId.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutId.getStatusCode().toString());
        Assertions.assertNotEquals(200, responseEntityWithoutId.getStatusCode());

        Assertions.assertNull(responseEntityWithoutname.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutId.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutname.getStatusCode().toString());

        Assertions.assertNull(responseEntityWithoutEmail.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutId.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutEmail.getStatusCode().toString());

        Assertions.assertNull(responseEntityWithoutPrepName.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutId.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutPrepName.getStatusCode().toString());

        Assertions.assertNull(responseEntityNull.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutId.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityNull.getStatusCode().toString());
    }
}
