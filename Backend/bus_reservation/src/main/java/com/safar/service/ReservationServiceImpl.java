
package com.safar.service;

import com.safar.exception.ReservationException;
import com.safar.exception.UserException;
import com.safar.model.*;
import com.safar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private BusRepository busRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Reservation addReservation(ReservationDTO dto, String key) throws ReservationException {

        CurrentUserSession userSession = currentUserSessionRepository.findByUuid(key);
        
        if(userSession == null) throw new ReservationException("Invalid Session key for user");
        
        Integer userIdInteger = userSession.getUserID();
        
        Optional<User> optional = userRepository.findById(userIdInteger);
        
        if(optional.isEmpty()) throw new ReservationException("User not found with the session key");
        
        User user = optional.get(); 
        
        Bus bus = busRepository.findByRouteFromAndRouteTo(dto.getSource(), dto.getDestination());
        
        if(bus == null) throw new ReservationException("Bus not found for the given starting to destination");
        
        Integer availableSeats = bus.getAvailableSeats();
        
        if(availableSeats < dto.getBookedSeat()) throw new ReservationException("Only " + availableSeats + " seats are available");
        
        availableSeats -= dto.getBookedSeat();
        
        bus.setAvailableSeats(availableSeats);
        
          Reservation reservation = new Reservation();

          if(dto.getJourneyDate().isBefore(LocalDate.now())) throw  new ReservationException("Journey Date should be in Future");

          reservation.setSource(dto.getSource());
          reservation.setDestination(dto.getDestination());
          reservation.setDate(dto.getJourneyDate());
          reservation.setStatus("Successful");
          reservation.setDate(LocalDate.now());
          reservation.setTime(LocalTime.now());
          reservation.setJourneyDate(dto.getJourneyDate());
          reservation.setBus(bus);
          reservation.setFare(bus.getFare() * dto.getBookedSeat());
          reservation.setBookedSeat(dto.getBookedSeat());
          reservation.setUser(user);

        return reservationRepository.save(reservation);
    }
 
    @Override
    public Reservation viewReservation(Integer rid, String key) throws ReservationException {
    	
    	CurrentAdminSession currentAdminSession = currentAdminSessionRepository.findByaid(key);
    	
//    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
    	
    	if(currentAdminSession == null) throw new ReservationException("Invalid admin login key");
    	
//    	if(currentUserSession == null) throw new ReservationException("Invalid user login key");

        Optional<Reservation> optional = reservationRepository.findById(rid);

        if(optional.isEmpty()) throw new ReservationException("Reservation with given id is not found");

        return optional.get();
    }

    @Override
    public List<Reservation> getAllReservation(String key) throws ReservationException {

        CurrentAdminSession session = currentAdminSessionRepository.findByaid(key);

        if(session == null) throw new ReservationException("Please provide valid admin login key");

        List<Reservation> list = reservationRepository.findAll();

        if(list.isEmpty()) throw new ReservationException("Reservation Not found");

        return list;
    }

    @Override
    public List<Reservation> viewReservationByUerId(Integer uid, String key) throws ReservationException {
    	
    	CurrentAdminSession currentAdminSession = currentAdminSessionRepository.findByaid(key);
    	
    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
    	
    	if(currentAdminSession == null && currentUserSession == null) throw new ReservationException("Invalid login key");
    	
//    	if(currentUserSession == null) throw new ReservationException("Invalid user login key");
    	
    	Optional<User> optional = userRepository.findById(uid);
    	
    	if(optional.isEmpty()) throw new ReservationException("User not find with given user id: " + uid);

    	User user = optional.get();
    	
    	List<Reservation> reservations = user.getReservationList();
    	
    	if(reservations.isEmpty()) throw new ReservationException("Reservation not found for this user");

        return reservations;
    }

    @Override
    public Reservation deleteReservation(Integer rid, String key) throws ReservationException{
    	
    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
    	
    	if(currentUserSession == null) throw new ReservationException("Invalid session key"); 
    	
    	Optional<Reservation> optional =  reservationRepository.findById(rid);  
    	
    	if(optional.isEmpty()) throw new ReservationException("Reservation not found with the given id: " + rid);
    	
    	Reservation reservation = optional.get();
    	
    	if(reservation.getJourneyDate().isBefore(LocalDate.now())) throw new ReservationException("Reservation Already Expired");
    	
    	Integer n = reservation.getBus().getAvailableSeats();
    	
    	reservation.getBus().setAvailableSeats(n + reservation.getBookedSeat());
    	
    	Bus bus = reservation.getBus();
    	
    	busRepository.save(bus);    	
        reservationRepository.delete(reservation);
        
        return reservation;
    }

    @Override
    public Reservation updateReservation(Integer rid, ReservationDTO dto, String key) throws ReservationException {
    	
    	CurrentUserSession userSession = currentUserSessionRepository.findByUuid(key);
        
        if(userSession == null) throw new ReservationException("Invalid Session key for user");
        
        Bus bus = busRepository.findByRouteFromAndRouteTo(dto.getSource(), dto.getDestination());
        
        if(bus == null) throw new ReservationException("Bus not found for the given starting to destination");
        
        Integer availableSeats = bus.getAvailableSeats();
        
        if(availableSeats < dto.getBookedSeat()) throw new ReservationException("Only " + availableSeats + " seats are available");
        
        availableSeats -= dto.getBookedSeat();
        
        bus.setAvailableSeats(availableSeats);
        
        Optional<Reservation> optional = reservationRepository.findById(rid);
        
        if(optional.isEmpty()) throw new ReservationException("Reservation not found with the given id: " + rid);
         
        Reservation reservation  = optional.get();
        reservation.setBookedSeat(dto.getBookedSeat());
        reservation.setBus(bus);
        reservation.setDate(dto.getJourneyDate());
        reservation.setDestination(dto.getDestination()) ;
        reservation.setFare(bus.getFare());
        reservation.setJourneyDate(dto.getJourneyDate());
        reservation.setSource(dto.getSource());
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());        
        
        reservationRepository.save(reservation);
        
        return reservation;
    }


}
