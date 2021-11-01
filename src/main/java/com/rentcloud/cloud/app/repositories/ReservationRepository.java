/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.repositories;

import com.rentcloud.cloud.app.entities.Client;
import com.rentcloud.cloud.app.entities.Reservation;
import com.rentcloud.cloud.app.reports.CountClient;
import com.rentcloud.cloud.app.repositories.crud.ReservationCrudRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author luigi
 */

@Repository
@NoArgsConstructor
@AllArgsConstructor

public class ReservationRepository {
  
    @Autowired
    private ReservationCrudRepository repository;
    
    
    /**
     * SELECT * FROM
     * @return Retorna todos los registros de la tabla
     */
    public List<Reservation> getAll(){
        return (List<Reservation>) repository.findAll();
    }
    
    /**
     * SELECT * FROM TABLE WHERE ID=id
     * @param id
     * @return Retorna un registro por el ID
     */
    public Optional<Reservation> getReservation(int id){
        return repository.findById(id);
    }
    
    
    /**
     * INSERT & UPDATE
     * @param reservation
     * @return Reservation Actualiza registro ya existente o Crea un nuevo registro
     */
    public Reservation save(Reservation reservation){
        return repository.save(reservation);
    }
    
    /**
     * DELETE FROM TABLE
     * @param reservation 
     */
    public void delete (Reservation reservation){
        repository.delete(reservation);
    }

    public List<Reservation> getReservationByStatus(String status){
        return repository.findAllByStatus(status);
    }
    
    public List<Reservation> getReservationPeriod(Date dateOne, Date dateTwo){
        return repository.findAllByStartDateAfterAndStartDateBefore(dateOne,dateTwo);
    }

    public List<CountClient> getTopClients(){
        List<CountClient> clientList = new ArrayList<>();
        List<Object[]> report = repository.countTotalReservationByClient();
        for(int i=0;i<report.size();i++){
            clientList.add(new CountClient((Long) report.get(i)[1],(Client)report.get(i)[0]));
            }
        return clientList;
    }

}
