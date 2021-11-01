/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services;

import com.rentcloud.cloud.app.entities.Reservation;
import com.rentcloud.cloud.app.reports.CountClient;
import com.rentcloud.cloud.app.reports.ReservationStatus;
import com.rentcloud.cloud.app.repositories.ReservationRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luigi
 */

@Service

public class ReservationService {
    
    @Autowired
    private ReservationRepository repository;
    
    /**
     * GET
     * @return 
     */
    public List<Reservation> getAll(){
        return repository.getAll();
    }
    
    /**
     * GET/{id}
     * @param reservationId
     * @return 
     */
    public Optional<Reservation> getReservation(int reservationId){
        return repository.getReservation(reservationId);
    }
    
    /**
     *POST
     * @param reservation
     * @return 
     */
    public Reservation save(Reservation reservation){
        if(reservation.getIdReservation()==null){
            return repository.save(reservation);
        }else{
            Optional<Reservation> existReservation = repository.getReservation(reservation.getIdReservation());
            if(existReservation.isPresent()){
                return reservation;
            }else{
                return repository.save(reservation);
            }
        }
    }
    
    /**
     * PUT (UPDATE)
     * @param reservation
     * @return 
     */
    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> existReservation = repository.getReservation(reservation.getIdReservation());
            if(existReservation.isPresent()){
                if(reservation.getStartDate() !=null){
                    existReservation.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate() !=null){
                    existReservation.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus() !=null){
                    existReservation.get().setStatus(reservation.getStatus());
                }
                 if(reservation.getClient() !=null){
                    existReservation.get().setClient(reservation.getClient());
                }
                  if(reservation.getCloud() !=null){
                    existReservation.get().setCloud(reservation.getCloud());
                }
                  if(reservation.getScore() !=null){
                    existReservation.get().setScore(reservation.getScore());
                } 
                return repository.save(existReservation.get());
            }else{
                return reservation;
            }
        }else{
            return reservation;
        }
    }
    
    /**
     * DELETE
     * @param reservationId
     * @return 
     */
    public boolean delete(int reservationId){
        Boolean respuesta = getReservation(reservationId).map(reservation ->{
            repository.delete(reservation);
            return true;
        }).orElse(false);
        return respuesta;
    }

    
    public ReservationStatus   getReservationStatusReport(){
        List<Reservation> completed = repository.getReservationByStatus("completed");
        List<Reservation> cancelled = repository.getReservationByStatus("cancelled");
        return new ReservationStatus(completed.size(),cancelled.size());
    }
    
    public List<Reservation> getReservationPeriod(String dateOne,String dateTwo){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = dateFormat.parse(dateOne);
            Date endDate = dateFormat.parse(dateTwo);
            if(startDate.before(endDate)){
                return repository.getReservationPeriod(startDate,endDate);
            }
        }catch (Exception e){
               e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<CountClient> getTopClients(){
       return repository.getTopClient();
    }
    
}
