package com.safar.controller;

import java.util.List;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.safar.exception.BusException;
import com.safar.exception.FeedBackException;
import com.safar.exception.UserException;
import com.safar.model.Feedback;
import com.safar.service.FeedbackService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/safar")
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	
	@PostMapping("/user/feedback/add/{busId}")
	public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody Feedback feedback,
												@PathVariable("busId") Integer busId,
												@RequestParam(required = false) String key) throws UserException,BusException{
		
		Feedback feedback2 = feedbackService.addFeedBack(feedback,busId,key);
		
		return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);
		
	}
	
	
	
	@PutMapping("/user/feedback/update")
	public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback,@RequestParam(required = false) String key) throws FeedBackException, UserException {
		
		Feedback feedback2 = feedbackService.updateFeedBack(feedback,key);
		
		return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/user/feedback/delete/{id}")
	public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id") Integer feedbackId,@RequestParam(required = false) String key) throws FeedBackException, UserException {
		
		Feedback feedback2 = feedbackService.deleteFeedBack(feedbackId,key);
		
		return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);
		
	}

	
//	viewFeedback
	
	@GetMapping("/feedback/{id}")
	public ResponseEntity<Feedback> viewFeedback(@PathVariable("id") Integer ID) throws FeedBackException {
		
		Feedback feedback2 = feedbackService.viewFeedback(ID);
		
		return new ResponseEntity<Feedback>(feedback2,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/feedback/all")
	public ResponseEntity<List<Feedback>> viewFeedbackAll() throws FeedBackException {
		
		List<Feedback> feedback2 =  feedbackService.viewFeedbackAll();
		
		return new ResponseEntity<List<Feedback>>(feedback2,HttpStatus.ACCEPTED);
		
	}
	
}