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

import java.util.ArrayList;
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
    public void shouldCheckIfTagsExistHasSizeOfTwoTest() {

        var allTagsFromQuestion = tagService.checkIfTagsExist(tags);

        Assertions.assertNotNull(allTagsFromQuestion);
        Assertions.assertFalse(allTagsFromQuestion.isEmpty());
        Assertions.assertTrue(allTagsFromQuestion.size() == 2);
    }

    @Test
    public void shouldCheckIfTagsExistHasSizeOfOneTest() {

        var  oneTagInlist = List.of(new Tag("Tag1"));
        var allTagsFromQuestion = tagService.checkIfTagsExist(oneTagInlist);

        Assertions.assertNotNull(allTagsFromQuestion);
        Assertions.assertFalse(allTagsFromQuestion.isEmpty());
        Assertions.assertTrue(allTagsFromQuestion.size() == 1);
    }


    @Test
    public void shouldCheckIfTagsExistHasRightContentTest() {

        var  oneTagInlist = List.of(new Tag("Tag4"));
        var allTagsFromQuestion = tagService.checkIfTagsExist(oneTagInlist);

        Assertions.assertNotNull(allTagsFromQuestion);
        Assertions.assertFalse(allTagsFromQuestion.isEmpty());
        Assertions.assertTrue(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("TAG4"));
    }

    @Test
    public void shouldCheckIfTagsExistHasUpperCaseCharacters() {

        var  oneTagInlist = List.of(new Tag("tag5"));
        var allTagsFromQuestion = tagService.checkIfTagsExist(oneTagInlist);

        Assertions.assertNotNull(allTagsFromQuestion);
        Assertions.assertFalse(allTagsFromQuestion.isEmpty());
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("tag5"));
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("tAg5"));
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("Tag5"));
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("tAG5"));
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("TAg5"));
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("TaG5"));
        Assertions.assertFalse(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("taG5"));
        Assertions.assertTrue(allTagsFromQuestion.get(allTagsFromQuestion.size()-1).getName().contains("TAG5"));
    }


    @Test
    public void shouldNotCheckIfTagsExistTest() {

        var allTagsFromQuestion = tagService.checkIfTagsExist(new ArrayList<Tag>());

        Assertions.assertNotNull(allTagsFromQuestion);
        Assertions.assertTrue(allTagsFromQuestion.isEmpty());
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
        Assertions.assertEquals(200, tags.getStatusCodeValue());
    }

    @Test
    public void shouldFindTagByNameWithLowerCaseCharacterTest() {

        ResponseEntity<Tag> tags = tagService.getTagByName("tag1");

        Assertions.assertNotNull(tags);
        Assertions.assertEquals("TAG1", tags.getBody().getName());
        Assertions.assertEquals(200, tags.getStatusCodeValue());
    }

    @Test
    public void shouldFindTagByNameWithDifferentFormsest() {

        ResponseEntity<Tag> tags1 = tagService.getTagByName("tag1");
        ResponseEntity<Tag> tags2 = tagService.getTagByName("tAg1");
        ResponseEntity<Tag> tags3 = tagService.getTagByName("TAg1");
        ResponseEntity<Tag> tags4 = tagService.getTagByName("TaG1");
        ResponseEntity<Tag> tags5 = tagService.getTagByName("taG1");
        ResponseEntity<Tag> tags6 = tagService.getTagByName("tAG1");
        ResponseEntity<Tag> tags7 = tagService.getTagByName("TAG1");

        Assertions.assertNotNull(tags1);
        Assertions.assertEquals("TAG1", tags1.getBody().getName());
        Assertions.assertEquals(200, tags1.getStatusCodeValue());


        Assertions.assertNotNull(tags2);
        Assertions.assertEquals("TAG1", tags2.getBody().getName());
        Assertions.assertEquals(200, tags2.getStatusCodeValue());


        Assertions.assertNotNull(tags3);
        Assertions.assertEquals("TAG1", tags3.getBody().getName());
        Assertions.assertEquals(200, tags3.getStatusCodeValue());


        Assertions.assertNotNull(tags4);
        Assertions.assertEquals("TAG1", tags4.getBody().getName());
        Assertions.assertEquals(200, tags4.getStatusCodeValue());


        Assertions.assertNotNull(tags5);
        Assertions.assertEquals("TAG1", tags5.getBody().getName());
        Assertions.assertEquals(200, tags5.getStatusCodeValue());


        Assertions.assertNotNull(tags6);
        Assertions.assertEquals("TAG1", tags6.getBody().getName());
        Assertions.assertEquals(200, tags6.getStatusCodeValue());


        Assertions.assertNotNull(tags7);
        Assertions.assertEquals("TAG1", tags7.getBody().getName());
        Assertions.assertEquals(200, tags7.getStatusCodeValue());

    }


    @Test
    public void shouldNotFindTagByNameTest() {

        ResponseEntity<Tag> tags = tagService.getTagByName("");

        Assertions.assertNotNull(tags);
        Assertions.assertNull(tags.getBody());
        Assertions.assertEquals(404, tags.getStatusCodeValue());
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


    @Test
    @Order(5)
    public void shouldFindAllEmptyListOfTags() {
        tagRepository.deleteAll();
        var tagsList = tagService.getAllTags();

        Assertions.assertNotNull(tagsList, "Question has Tags");
        Assertions.assertTrue(tagsList.isEmpty());
    }

    @Test
    @Order(6)
    public void shouldReturnNotFoundQuestionsAreEmpty() {

        questionRepository.deleteAll();
        ResponseEntity<List<Tag>> tagsRes = tagService.getTagsWithMostQuestions();

        Assertions.assertNotNull(tagsRes);
        Assertions.assertNull(tagsRes.getBody());
        Assertions.assertEquals(404, tagsRes.getStatusCodeValue());
    }

    @Test
    @Order(7)
    public void shouldReturnNotFoundTagsAreEmptyTest() {

        tagRepository.deleteAll();
        ResponseEntity<List<Tag>> tagsRes = tagService.getTagsWithMostQuestions();

        Assertions.assertNotNull(tagsRes);
        Assertions.assertNull(tagsRes.getBody());
        Assertions.assertEquals(404, tagsRes.getStatusCodeValue());
    }
}
