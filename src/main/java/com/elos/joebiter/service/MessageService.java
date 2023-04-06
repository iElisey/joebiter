package com.elos.joebiter.service;

import com.elos.joebiter.model.Message;
import com.elos.joebiter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<Message> getAll(Pageable page) {
        return messageRepository.findAllByOrderByIdDesc(page);
    }

    public Message getById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public Message create(Message message) {
        return messageRepository.save(message);
    }

    public void delete(Long id, UserDetailsImpl userDetails) {
        if (messageRepository.findById(id).orElseThrow().getAuthor().getId().equals(userDetails.getId())) {
            messageRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete a message other user!");
        }
    }

    public Page<Message> findByQuery(String query, Pageable page) {
        return messageRepository.findByTextContainingIgnoreCaseOrderByIdDesc(query, page);
    }
//
//    public void setMessage(Long id, Message message) {
//        Message findedMessage = messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found!"));
//        if (Objects.equals(findedMessage.getId(), message.getId())) {
//            findedMessage = message;
//        }
//        messageRepository.save(findedMessage);
//    }

}
