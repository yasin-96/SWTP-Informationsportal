package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ComponentScan()
@DataMongoTest
@ContextConfiguration
class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private List<Tag> tags;

    @BeforeEach
    public void Setup() {
        tagRepository.deleteAll();
        questionRepository.deleteAll();

        tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

        tagRepository.save(tags.get(0));
        tagRepository.save(tags.get(1));

        var question = new Question("Header1", "Content1", tags, "USER1", "USER1");
        questionRepository.save(question);

    }

    @Test
    public void shouldCheckIfTagsExistTest() {

        var allTagsFromQuestion = tagService.checkIfTagsExist(tags);

        Assertions.assertNotNull(allTagsFromQuestion);
        Assertions.assertFalse(allTagsFromQuestion.isEmpty());
    }


    @Test
    public void shouldFindAllTags() {

        var tagsList = tagService.getAllTags();

        Assertions.assertNotNull(tagsList, "Question has Tags");
        Assertions.assertFalse(tagsList.isEmpty());
    }


    @Test
    public void shouldFindTagByNameTest() {

        ResponseEntity<Tag> tags = tagService.getTagByName("TAG1");

        Assertions.assertNotNull(tags);
        Assertions.assertEquals("TAG1", tags.getBody().getName());
    }


    @Test
    @Order(3)
    public void shouldReturnTagToUpperCaseTest() {

        Tag tag = tagService.tagToUpperCase(tags.get(0));

        Assertions.assertNotNull(tag);
        Assertions.assertEquals("TAG1", tag.getName());
    }

    @Test
    @Order(4)
    public void shouldReturnTagsWithMostQuestionsTest() {

        ResponseEntity<List<Tag>> tagsRes = tagService.getTagsWithMostQuestions();

        Assertions.assertNotNull(tagsRes);
        Assertions.assertEquals("TAG2", tagsRes.getBody().get(0).getName());
        Assertions.assertTrue(tagsRes.hasBody());
        Assertions.assertEquals(200, tagsRes.getStatusCodeValue());
    }
}
