package com.bunny.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunny.questionservice.doa.QuestionDao;
import com.bunny.questionservice.model.Question;
import com.bunny.questionservice.model.QuestionWrapper;
import com.bunny.questionservice.model.Response;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDoa;

	public ResponseEntity<List<Question>> getAllQuestions() {

		try {
			return new ResponseEntity<>(questionDoa.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<List<Question>> getQuestionByCategory(String category) {

		try {
			return new ResponseEntity<>(questionDoa.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<String> addQuestion(Question question) {

		try {
			questionDoa.save(question);
			return new ResponseEntity<>("Data Successfully Save", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);

	}

	public String deletQuestionById(Integer id) {
		questionDoa.deleteById(id);
		return "Data Successfully Delete " + id;
	}

	public ResponseEntity<List<Integer>> getQuestoinsForQuiz(String categoryName, Integer numQuestions) {

		List<Integer> questions = questionDoa.findRandomQuestionByCategory(categoryName, numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);

	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds) {

		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();

		for (Integer id : questionsIds) {
			questions.add(questionDoa.findById(id).get());
		}

		for (Question question : questions) {

			QuestionWrapper wrapper = new QuestionWrapper();

			wrapper.setId(question.getId());
			wrapper.setLevel(question.getLevel());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());

			wrappers.add(wrapper);

		}

		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {

		int right = 0;

		for (Response resp : responses) {
			Question question = questionDoa.findById(resp.getId()).get();

			if (resp.getResponse().equals(question.getAnswer())) {
				right++;

			}

		}

		return new ResponseEntity<>(right, HttpStatus.OK);

		
	}

}
