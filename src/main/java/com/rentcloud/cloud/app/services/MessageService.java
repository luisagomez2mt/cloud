/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services;

import com.rentcloud.cloud.app.entities.Message;
import com.rentcloud.cloud.app.repositories.MessageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author luigi
 */

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository repository;

    /**
     * GET
     *
     * @return
     */
    public List<Message> getAll() {
        return repository.getAll();
    }

    /**
     * GET(id)
     *
     * @param messageId
     * @return
     */
    public Optional<Message> getMessage(int messageId) {
        return repository.getMessage(messageId);
    }

    /**
     * POST
     *
     * @param message
     * @return
     */
    public Message save(Message message) {
        if (message.getIdMessage() == null) {
            return repository.save(message);
        } else {
            Optional<Message> existMessage = repository.getMessage(message.getIdMessage());
            if (existMessage.isPresent()) {
                return message;
            } else {
                return repository.save(message);
            }
        }
    }

    /**
     * PUT (UPDATE)
     *
     * @param message
     * @return
     */
    public Message update(Message message) {
        if(message.getIdMessage()!=null){
            Optional<Message> existMessage = repository.getMessage(message.getIdMessage());
            if(existMessage.isPresent()){
                if(message.getMessageText()!=null){
                    existMessage.get().setMessageText(message.getMessageText());
                }
                if(message.getClient() !=null){
                    existMessage.get().setClient(message.getClient());
                }
                return repository.save(existMessage.get());
            }else{
                return message;
            }
        }else{
            return message;
        }
    }

    /**
     * DELETE
     *
     * @param messageId
     * @return
     */
    public boolean delete(int messageId) {
        Boolean respuesta = getMessage(messageId).map(message -> {
            repository.delete(message);
            return true;
        }).orElse(false);
        return respuesta;
    }

    
}
