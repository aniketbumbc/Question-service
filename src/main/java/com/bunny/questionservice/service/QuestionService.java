package com.bunny.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunny.questionservice.doa.QuestionDao;
import com.bunny.questionservice.model.Question;



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

}
