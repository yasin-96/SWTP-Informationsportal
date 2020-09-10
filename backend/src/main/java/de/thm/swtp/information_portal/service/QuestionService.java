package de.thm.swtp.information_portal.service;

import de.thm.swtp.information_portal.Util;
import de.thm.swtp.information_portal.models.Question.Question;
import de.thm.swtp.information_portal.models.Tag.Tag;
import de.thm.swtp.information_portal.repositories.AnswerRepository;
import de.thm.swtp.information_portal.repositories.QuestionRepository;
import de.thm.swtp.information_portal.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static de.thm.swtp.information_portal.Util.checkExistQuestionModel;
import static de.thm.swtp.information_portal.Util.checkQuestionModel;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AnswerRepository answerRepository;


    public ResponseEntity<HashSet<Question>> findByManyTagNames(String searchQuery) {

        if(searchQuery.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }


        var listQuery = Arrays.stream(searchQuery.toUpperCase().split(" "))
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());

        var filteredQuestions = new HashSet<Question>();

        for (var query : listQuery) {
            var foundedTag = this.findTag(query);
            if (foundedTag != null) {
                filteredQuestions.addAll(foundedTag);
            }
        }

        if (filteredQuestions.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filteredQuestions);
    }

    public List<Question> findTag(String tagToFind) {

        if(tagToFind.isEmpty()){
            return null;
        }

        var questionByTags = new ArrayList<Question>();
        var existingTag = tagRepository.findByName(tagToFind);

        if (existingTag != null) {
            var allQuestions = questionRepository.findAll();

            if (allQuestions.isEmpty()) {
                return null;
            }

            questionByTags = new ArrayList<>();
            for (var question : allQuestions) {
                for (Tag tag : question.getTags()) {
                    var check = existingTag.getName().toLowerCase().contains(tag.getName().toLowerCase());
                    if (check) {
                        questionByTags.add(question);
                    }
                }
            }
            return questionByTags;
        }
        return null;
    }

    public ResponseEntity<HashSet<Question>> findAllTags(String tagToFind) {

        var response = this.findByManyTagNames(tagToFind);

        if (response.getBody().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.getBody());
    }

    /**
     * @return
     */
    public ResponseEntity<List<Question>> getAllQuestions() {
        var allQuestions = questionRepository.findAll();

        if (allQuestions.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allQuestions);
    }

    /**
     * @param question
     * @return
     */
    public ResponseEntity<Question> editQuestion(Question question) {

        if(question == null || !checkExistQuestionModel(question)){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        var tagList = tagService.checkIfTagsExist(question.getTags());
        question.setTags(tagList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questionRepository.save(question));

    }

    /**
     * @param question
     * @return
     */
    public ResponseEntity<Question> postQuestion(Question question, String userId, String userName) {

        if(!checkQuestionModel(question)
                || userId.isEmpty()
                || userName.isEmpty()
        ){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        var newQuestionTags = tagService.checkIfTagsExist(question.getTags());

        var newQuestion = new Question(
                question.getHeader(),
                question.getContent(),
                newQuestionTags,
                userId,
                userName
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questionRepository.save(newQuestion));
    }

    /**
     * @param id
     * @return
     */
    public ResponseEntity<Optional<Question>> getQuestion(String id) {

        if(id.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        var question = questionRepository.findById(id);

        if (question.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(question);
    }

    /**
     *
     * @return
     */
    public ResponseEntity<List<Question>> mostActiveQuestions() {
        var allQuestions = questionRepository.findAll();
        var map = new HashMap<Question, Integer>();

        if(allQuestions.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        for (var item : allQuestions) {
            answerRepository.findById(item.getId())
                            .ifPresent(value ->
                                    map.put(item, value.getListOfAnswers().size())
                            );
        }

        if(map.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getListOfMostActiveQuestions(map));
    }

    /**
     * @param map map as parameter for our mostActiveQuestions() method
     * @return Returns        the sorted list for our mostActiveQuestions() method
     */
    public List<Question> getListOfMostActiveQuestions(HashMap<Question, Integer> map) {
        return map
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new)
                )

                //get only the key
                .keySet()
                .stream()

                //take only 20 items and return as list
                .limit(20)
                .collect(Collectors.toList());
    }
}
