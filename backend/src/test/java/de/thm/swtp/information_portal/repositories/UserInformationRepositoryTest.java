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

import de.thm.swtp.information_portal.models.User.UserInformation;

@DataMongoTest
class UserInformationRepositoryTest {

	private static final Logger LOGGER=LoggerFactory.getLogger(UserInformationRepositoryTest.class);

	@Autowired
	private UserInformationRepository   userInformationRepository;

	@BeforeEach
	public void setUp() {
		userInformationRepository.deleteAll();
	}

	@Test
	public void shouldInsertUserInformationTest() {

		LOGGER.info("\n\n **********Start insertUserInformation Test********** \n\n");

		UserInformation userInformationOne = new UserInformation("12", 12, 12);
		UserInformation userInformationTwo = new UserInformation("10", 2, 2);

		UserInformation userOne = userInformationRepository.save(userInformationOne);
		UserInformation userTwo = userInformationRepository.save(userInformationTwo);

		Assertions.assertEquals(userInformationOne.getId(), userOne.getId(), "Ids should be equals");
		Assertions.assertEquals(userInformationTwo.getId(), userTwo.getId(), "Ids should be equals");

		Assertions.assertNotNull(userInformationOne , "userInformationOne should be not null");
		Assertions.assertNotNull(userInformationTwo , "userInformationTwo should be not null");

		Assertions.assertEquals(12, userInformationOne.getNumberOfQuestions());
		Assertions.assertEquals(2, userInformationTwo.getNumberOfQuestions());

		LOGGER.info("\n\n **********End insertUserInformation Test********** \n\n");
	}


	@Test
	public void shouldGetUserinformationByIdTest() {

		LOGGER.info("\n\n **********Start GetUserinformationById Test********** \n\n");

		UserInformation userInformationOne = new UserInformation("12", 12, 12);

		UserInformation user = userInformationRepository.save(userInformationOne);
		
		Assertions.assertEquals(12 , userInformationRepository.findById(user.getId()).get().getNumberOfAnswers(),
				"Successfully fetched userInformation by ID");

		LOGGER.info("\n\n **********End GetUserinformationById Test********** \n\n");
	}

	@Test
	public void shouldUpdateUserInformationTest() {

		LOGGER.info("\n\n **********Start UpdateUserInformation Test********** \n\n");

		UserInformation userInformationOne = new UserInformation("12", 12, 12);

		UserInformation user = userInformationRepository.save(userInformationOne);
		
        user.setId("13");
        user.setNumberOfAnswers(13);

		Assertions.assertEquals("13", userInformationRepository.save(user).getId(),
				"Successfully updated Tag");

		LOGGER.info("\n\n **********End UpdateUserInformation Test********** \n\n");
	}

	@Test
	public void shouldGetAllUserInformationTest() {

		LOGGER.info("\n\n **********Start GetAllUserInformation Test********** \n\n");

		UserInformation userInformationOne = new UserInformation("12", 12, 12);
		UserInformation userInformationTwo = new UserInformation("10", 2, 2);

		userInformationRepository.save(userInformationOne);
		userInformationRepository.save(userInformationTwo);

		List<UserInformation> userInformations = userInformationRepository.findAll();

		Assertions.assertEquals(2, userInformations.size(),
				"Successfully fetched the list of userInformations");
		
		Assertions.assertEquals("12" , userInformations.get(0).getId());

		LOGGER.info("\n\n **********End GetAllUserInformation Test********** \n\n");
	}

	@Test
	public void shouldDeleteUserInformationTest() {

		LOGGER.info("\n\n **********Start deleteUserInformation Test********** \n\n");

		UserInformation userInformationOne = new UserInformation("12", 12, 12);
		UserInformation userOne = userInformationRepository.save(userInformationOne);
		userInformationRepository.deleteById(userOne.getId());

		Assertions.assertEquals(Optional.empty(), userInformationRepository.findById(userOne.getId()),
				"Successfully deleted a single a UserInformation");

		LOGGER.info("\n\n **********End deleteUserInformation Test********** \n\n");
	}
		

}
