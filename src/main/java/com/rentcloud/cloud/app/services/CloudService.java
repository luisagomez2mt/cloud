/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services;

import com.rentcloud.cloud.app.entities.Cloud;
import com.rentcloud.cloud.app.repositories.CloudRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luigi
 */

@Service
public class CloudService {
    
    @Autowired
    private CloudRepository repository;
    
    /**
     * GET
     * @return 
     */
    public List<Cloud> getall(){
        return repository.getAll();
    }
    
    /**
     * GET(id)
     * @param cloudId
     * @return 
     */
    public Optional<Cloud> getCloud(int cloudId){
        return repository.getCloud(cloudId);
    }
    
    /**
     *POST
     * @param cloud
     * @return 
     */
    public Cloud save(Cloud cloud){
        if(cloud.getId()==null){
            return repository.save(cloud);
        }else{
            Optional<Cloud> existCloud = repository.getCloud(cloud.getId());
            if(existCloud.isPresent()){
                return cloud;
            }else{
                return repository.save(cloud);
            }
        }
    }
    
    /**
     * PUT (UPDATE)
     * @param cloud
     * @return 
     */
    public Cloud update(Cloud cloud){
        if(cloud.getId()!=null){
            Optional<Cloud> existCloud = repository.getCloud(cloud.getId());
            if(existCloud.isPresent()){
                if(cloud.getName()!=null){
                    existCloud.get().setName(cloud.getName());
                }
                if(cloud.getBrand()!=null){
                    existCloud.get().setBrand(cloud.getBrand());
                }
                if(cloud.getCategory()!=null){
                    existCloud.get().setCategory(cloud.getCategory());
                }
                if(cloud.getDescription()!=null){
                    existCloud.get().setDescription(cloud.getDescription());
                }
                if(cloud.getMessages()!=null){
                    existCloud.get().setMessages(cloud.getMessages());
                }
                if(cloud.getReservations()!=null){
                    existCloud.get().setReservations(cloud.getReservations());
                }
                if(cloud.getYear()!=null){
                    existCloud.get().setYear(cloud.getYear());
                }
                return repository.save(existCloud.get());
            }else {
                return cloud;
            }
        }else{
            return cloud;
        }
    }
    
    /**
     * DELETE
     * @param cloudId
     * @return 
     */
    public boolean delete(int cloudId){
        Boolean respuesta = getCloud(cloudId).map(cloud->{
            repository.delete(cloud);
            return true;
        }).orElse(false);
        return respuesta;
    }
 
    
}
