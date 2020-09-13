package de.thm.swtp.information_portal.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import de.thm.swtp.information_portal.models.User.User;

@DataMongoTest
class UserRepositoryTest {

	private static final Logger LOGGER=LoggerFactory.getLogger(UserRepositoryTest.class);

	@Autowired
	private UserRepository   userRepository;

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
	}

	@Test
	public void shouldInsertUserTest() {

		LOGGER.info("\n\n **********Start insertUser Test********** \n\n");

		User userOne = new User("12", "user1", "user1@gmail.com", "usr1", 1233333L);
		User userTwo = new User("13", "user2", "user2@gmail.com", "usr2", 6233333L);

		userRepository.save(userOne);
		userRepository.save(userTwo);

		Assertions.assertEquals(userOne.getId(), userOne.getId(), "Ids should be equals");
		Assertions.assertEquals(userTwo.getId(), userTwo.getId(), "Ids should be equals");

		Assertions.assertNotNull(userOne , "userOne should be not null");
		Assertions.assertNotNull(userTwo , "userTwo should be not null");

		Assertions.assertEquals("user1", userOne.getName());
		Assertions.assertEquals("user2", userTwo.getName());

		LOGGER.info("\n\n **********End insertUser Test********** \n\n");
	}


	@Test
	public void shouldGetUserByIdTest() {

		LOGGER.info("\n\n **********Start GetUserById Test********** \n\n");

		User userOne = new User("12", "user1", "user1@gmail.com", "usr1", 1233333L);
		User userResOne = userRepository.save(userOne);
		
		Assertions.assertEquals("user1" , userRepository.findById(userResOne.getId()).get().getName(),
				"Successfully fetched user by ID");

		LOGGER.info("\n\n **********End GetUserById Test********** \n\n");
	}

	@Test
	public void shouldUpdateUserTest() {

		LOGGER.info("\n\n **********Start UpdateUser Test********** \n\n");

		User userOne = new User("12", "user1", "user1@gmail.com", "usr1", 1233333L);
		userRepository.save(userOne);
		
		userOne.setId("13");
		userOne.setName("user12");

		Assertions.assertEquals("user12", userRepository.save(userOne).getName(),
				"Successfully updated Tag");

		LOGGER.info("\n\n **********End UpdateUser Test********** \n\n");
	}

	@Test
	public void shouldGetAllUserTest() {

		LOGGER.info("\n\n **********Start GetAllUser Test********** \n\n");

		User userOne = new User("12", "user1", "user1@gmail.com", "usr1", 1233333L);
		User userTwo = new User("13", "user2", "user2@gmail.com", "usr2", 6233333L);

		userRepository.save(userOne);
		userRepository.save(userTwo);

		List<User> users = userRepository.findAll();

		Assertions.assertEquals(2, users.size(),
				"Successfully fetched the list of users");
		
		Assertions.assertEquals("12" , users.get(0).getId());

		LOGGER.info("\n\n **********End GetAllUser Test********** \n\n");
	}

	@Test
	public void shouldDeleteUserTest() {

		LOGGER.info("\n\n **********Start deleteUser Test********** \n\n");

		User userOne = new User("12", "user1", "user1@gmail.com", "usr1", 1233333L);
		userRepository.save(userOne);
		
		userRepository.deleteById(userOne.getId());

		Assertions.assertEquals(Optional.empty(), userRepository.findById(userOne.getId()),
				"Successfully deleted a single a User");

		LOGGER.info("\n\n **********End deleteUser Test********** \n\n");
	}
		

}
