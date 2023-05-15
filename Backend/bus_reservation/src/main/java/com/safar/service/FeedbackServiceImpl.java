package com.safar.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.safar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safar.exception.BusException;
import com.safar.exception.UserException;
import com.safar.model.Bus;
import com.safar.model.CurrentUserSession;
import com.safar.model.User;

import com.safar.repository.BusRepository;
import com.safar.repository.CurrentUserSessionRepository;
import com.safar.repository.UserRepository;

import com.safar.exception.FeedBackException;
import com.safar.model.Feedback;
import com.safar.repository.UserRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository fdao;

	@Autowired
	private UserRepository userDao;

	@Autowired
	private BusRepository busDao;
	
	@Autowired
	private CurrentUserSessionRepository userSessionDao;

	@Override
	public Feedback addFeedBack(Feedback feedBack, Integer busId, String key) throws BusException, UserException {
		
		CurrentUserSession loggedInUser= userSessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new UserException("Please provide a valid key to give Feedback!");
		}
		
		User user = userDao.findById(loggedInUser.getUserID()).orElseThrow(()-> new UserException("User not found!"));

		Optional<Bus> busOptional = busDao.findById(busId);
		if (busOptional.isEmpty()) {
			throw new BusException("Bus is not present with Id: "+ busId);
		}

		feedBack.setBus(busOptional.get());
		feedBack.setUser(user);
		feedBack.setFeedbackDateTime(LocalDateTime.now());
		Feedback savedFeedback = fdao.save(feedBack);

		return savedFeedback;
	}

	@Override
	public Feedback updateFeedBack(Feedback feedback,String key) throws FeedBackException, UserException {
		
		CurrentUserSession loggedInUser= userSessionDao.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new UserException("Please provide a valid key to update Feedback!");
		}
		
		User user = userDao.findById(loggedInUser.getUserID()).orElseThrow(()-> new UserException("User not found!"));

		Optional<Feedback> opt = fdao.findById(feedback.getFeedBackId());
		
		if (opt.isPresent()) {
			Feedback feedback2 = opt.get();
			Optional<Bus> busOptional = busDao.findById(feedback2.getBus().getBusId());
			if(!busOptional.isPresent()) throw new FeedBackException("Invalid bus details!");
			feedback.setBus(busOptional.get());
			feedback.setUser(user);
			user.getFeedbackList().add(feedback);
			feedback.setFeedbackDateTime(LocalDateTime.now());

			return fdao.save(feedback);
		}
		else
		    throw new FeedBackException("No feedback found!");
	}

	@Override
	public Feedback viewFeedback(Integer id) throws FeedBackException {
		Optional<Feedback> fedOptional = fdao.findById(id);
		if (fedOptional.isPresent()) {
			return fedOptional.get();
		}
		throw new FeedBackException("No feedback found!");
	}

	@Override
	public List<Feedback> viewFeedbackAll() throws FeedBackException {
		Optional<List<Feedback>> fedOptional = Optional.of(fdao.findAll());
		if (fedOptional.isPresent()) {
			return fedOptional.get();
		}
		throw new FeedBackException("No feedbacks found!");
	}

	@Override
	public Feedback deleteFeedBack(Integer feedbackId, String key) throws FeedBackException, UserException {
		CurrentUserSession loggedInUser= userSessionDao.findByUuid(key);
		if(loggedInUser == null) {
			throw new UserException("Please provide a valid key to update Feedback!");
		}

		User user = userDao.findById(loggedInUser.getUserID()).orElseThrow(()-> new UserException("User not found!"));
		
		Optional<Feedback> fedOptional = fdao.findById(feedbackId);
		
		if (fedOptional.isPresent()) {
			Feedback existingFeedback = fedOptional.get();
			fdao.delete(existingFeedback);
			return existingFeedback;
		}
		throw new FeedBackException("No feedback found!");
	}
}
