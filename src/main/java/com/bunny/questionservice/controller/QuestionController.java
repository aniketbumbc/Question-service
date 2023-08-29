package com.bunny.questionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunny.questionservice.model.Question;
import com.bunny.questionservice.model.QuestionWrapper;
import com.bunny.questionservice.model.Response;
import com.bunny.questionservice.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@Autowired
	Environment environment;
	

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {

		return questionService.getAllQuestions();
	}

	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
		return questionService.getQuestionByCategory(category);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	@DeleteMapping("deleteQuestion/{id}")
	public String getQuestionByCategory(@PathVariable Integer id) {
		return questionService.deletQuestionById(id);
	}
	
	@GetMapping("genereate")
	public ResponseEntity <List<Integer>> getQuestoinsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
		
		return questionService.getQuestoinsForQuiz(categoryName,numQuestions);
		
	}
	
	
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer>questionsIds ){
		System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionsFromId(questionsIds);	
	}
	
	@PostMapping("getScore")
	public ResponseEntity <Integer> getScore(@RequestBody List<Response> responses){
		
		return questionService.getScore(responses);
	}

}
