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

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private User userNotExistTwo;
    private User userWithInValidDataWithOutId;
    private User userWithInValidDataWithOutName;
    private User userWithInValidDataWithOutEmail;
    private User userWithInValidDataWithOutPrepUserName;
    private User userWithInValidDataAllNull;
    private User userWithInValidDataIsNull;

    @BeforeEach
    public void Setup() {
        //clear all data in collection
        userRepository.deleteAll();

        //init new user
        userOne = new User("User1", "USER1", "user1@gmail.com", "usr1");
        userTwo = new User("User2", "USER2", "user2@gmail.com", "usr2");
        userThree = new User("User3", "USER3", "user3@gmail.com", "usr3");


        userNotExist = new User("User10", "USER10","user10@gmail.com", "usr10");
        userNotExistTwo = new User("", "USER20","user20@gmail.com", "usr20");

        userWithInValidDataWithOutId = new User("", "UserInva","UserInvalidID@gmail.com", "usrinvid");
        userWithInValidDataWithOutName = new User("UserInvalidName", "","UserInvalid@gmail.com", "usrinvname");
        userWithInValidDataWithOutEmail = new User("UserInvalidEmail", "UserInvEmail","", "usrinvemail");
        userWithInValidDataWithOutPrepUserName = new User("UserInvalidPrepName", "UserInvPrepName","UserInvalid@gmail.com", "");
        userWithInValidDataAllNull = new User("", "","", "");
        userWithInValidDataIsNull = null;


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
    public void shouldNotFindUserByIdTest() {

        var users = userService.getUserById("");

        Assertions.assertNotNull(users);
        Assertions.assertNull(users.getBody());
        Assertions.assertEquals(404, users.getStatusCodeValue());
    }

    @Test
    public void shouldNotFindUserWithNullPointerTest() {

        var users = userService.getUserById(null);

        Assertions.assertNotNull(users);
        Assertions.assertNull(users.getBody());
        Assertions.assertEquals(400, users.getStatusCodeValue());
    }


    @Test
    public void shouldFindLoggedInUserTest() throws URISyntaxException {

        var users = userService.getLoggedInUser(userOne.getId(), userOne);

        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.hasBody());
        Assertions.assertEquals(userOne.getName(), users.getBody().getName());
        Assertions.assertEquals(200, users.getStatusCodeValue());
    }

    @Test
    public void shouldFindLoggedInUserIfNotCreateTest() throws URISyntaxException {

        var users = userService.getLoggedInUser("", userNotExistTwo);
        Assertions.assertNotNull(users);
        Assertions.assertNotNull(users.getBody().getId());
        Assertions.assertTrue(users.hasBody());
        Assertions.assertEquals(userNotExistTwo.getName(), users.getBody().getName());
        Assertions.assertEquals(200, users.getStatusCodeValue());
    }

    @Test
    public void shouldFindLoggedInUserHasNotInvalidDataTest() throws URISyntaxException {

        var users = userService.getLoggedInUser("", null);
        Assertions.assertNotNull(users);
        Assertions.assertNull(users.getBody());
        Assertions.assertEquals(400, users.getStatusCodeValue());
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
        var responseEntityISNull = userService.saveUserIfNotExists(userWithInValidDataIsNull);

        Assertions.assertNotNull(responseEntityWithoutId.getBody());
        Assertions.assertNotNull(responseEntityWithoutId.getBody().getId());
        Assertions.assertEquals("200 OK", responseEntityWithoutId.getStatusCode().toString());

        Assertions.assertNull(responseEntityWithoutname.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutname.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutname.getStatusCode().toString());

        Assertions.assertNull(responseEntityWithoutEmail.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutEmail.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutEmail.getStatusCode().toString());

        Assertions.assertNull(responseEntityWithoutPrepName.getBody());
        Assertions.assertNotEquals(200, responseEntityWithoutPrepName.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityWithoutPrepName.getStatusCode().toString());

        Assertions.assertNull(responseEntityNull.getBody());
        Assertions.assertNotEquals(200, responseEntityNull.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityNull.getStatusCode().toString());

        Assertions.assertNull(responseEntityISNull.getBody());
        Assertions.assertNotEquals(200, responseEntityISNull.getStatusCode());
        Assertions.assertEquals("203 NON_AUTHORITATIVE_INFORMATION", responseEntityISNull.getStatusCode().toString());

    }
}
