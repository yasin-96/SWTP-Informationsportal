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

import de.thm.swtp.information_portal.models.Tag.Tag;

@DataMongoTest
class TagRepositoryTest {

	private static final Logger LOGGER=LoggerFactory.getLogger(TagRepositoryTest.class);

	@Autowired
	private TagRepository  tagRepository;

	@BeforeEach
	public void setUp() {
		tagRepository.deleteAll();
	}

	@Test
	public void shouldInsertTagTest() {

		LOGGER.info("\n\n **********Start insertTag Test********** \n\n");

		List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

		Tag tagOne = tagRepository.save(tags.get(0));
		Tag tagTwo = tagRepository.save(tags.get(1));

		Assertions.assertEquals(tags.get(0).getId(), tagOne.getId(), "Ids should be equals");
		Assertions.assertEquals(tagTwo.getId(), tagTwo.getId(), "Ids should be equals");

		Assertions.assertNotNull(tagOne , "TagOne should be not null");
		Assertions.assertNotNull(tagTwo , "TagTwo should be not null");

		Assertions.assertEquals("TAG1", tagOne.getName());
		Assertions.assertEquals("TAG2", tagTwo.getName());

		LOGGER.info("\n\n **********End insertTag Test********** \n\n");
	}


	@Test
	public void shouldGetTagByIdTest() {

		LOGGER.info("\n\n **********Start GetTagById Test********** \n\n");

		List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

		Tag tagOne = tagRepository.save(tags.get(0));

		Assertions.assertEquals("TAG1" , tagRepository.findById(tagOne.getId()).get().getName(),
				"Successfully fetched tag by ID");

		LOGGER.info("\n\n **********End GetTagById Test********** \n\n");
	}

	@Test
	public void shouldUpdateTagTest() {

		LOGGER.info("\n\n **********Start updateTag Test********** \n\n");

		List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));
        
        Tag tagOne = tagRepository.save(tags.get(0));

        tagOne.setName("Tag3");

		Assertions.assertEquals("Tag3", tagRepository.save(tagOne).getName(),
				"Successfully updated Tag");

		LOGGER.info("\n\n **********End updateTag Test********** \n\n");
	}

	@Test
	public void shouldGetAllTagsTest() {

		LOGGER.info("\n\n **********Start getAllTags Test********** \n\n");

		List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));
        
        tagRepository.save(tags.get(0));
        tagRepository.save(tags.get(1));

		List<Tag> tagsRes = tagRepository.findAll();

		Assertions.assertEquals(2, tagsRes.size(),
				"Successfully fetched the list of tags");
		
		Assertions.assertEquals("TAG1" , tagsRes.get(0).getName());

		LOGGER.info("\n\n **********End getAllTags Test********** \n\n");
	}

	@Test
	public void shouldDeleteTagTest() {

		LOGGER.info("\n\n **********Start deleteTag Test********** \n\n");

		List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));
        
        Tag tag = tagRepository.save(tags.get(0));
        tagRepository.save(tags.get(1));

		tagRepository.deleteById(tag.getId());

		Assertions.assertEquals(Optional.empty(), tagRepository.findById(tag.getId()),
				"Successfully deleted a single a comment");

		LOGGER.info("\n\n **********End deleteTag Test********** \n\n");
	}
	
	@Test
	public void shouldFindByNameTest() {

		LOGGER.info("\n\n **********Start FindByName Test********** \n\n");

		List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));
        
        tagRepository.save(tags.get(0));
        tagRepository.save(tags.get(1));

		Tag tagOne = tagRepository.findByName("TAG1");

		Assertions.assertEquals("TAG1" , tagOne.getName(),
				"Successfully fetched tag by Name");

		LOGGER.info("\n\n **********End FindByName Test********** \n\n");
	}

}
