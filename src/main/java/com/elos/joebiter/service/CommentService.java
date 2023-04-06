package com.elos.joebiter.service;

import com.elos.joebiter.model.Comment;
import com.elos.joebiter.model.Message;
import com.elos.joebiter.repository.CommentRepository;
import com.elos.joebiter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.elos.joebiter.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, MessageRepository messageRepository) {
        this.commentRepository = commentRepository;
        this.messageRepository = messageRepository;
    }

    public Comment create(Long messageId, Comment comment) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            comment.setMessage(message.get());
            return commentRepository.save(comment);
        } else {
            throw new NotFoundException("Message not found");
        }
    }

    public List<Comment> getAllCommentsByMessageId(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            return message.get().getComments();
        } else {
            throw new NotFoundException("Message not found");
        }
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void delete(Long id, UserDetailsImpl userDetails) {
        if (commentRepository.findById(id).orElseThrow().getAuthor().getId().equals(userDetails.getId())) {
            commentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete a comment other user!");

        }
    }

}
