package com.elos.joebiter.controller;

import com.elos.joebiter.model.Comment;
import com.elos.joebiter.repository.UserRepository;
import com.elos.joebiter.service.CommentService;
import com.elos.joebiter.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/message/{messageId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    public CommentController(CommentService commentService,
                             UserRepository userRepository) {
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Comment createComment(@PathVariable Long messageId, @RequestBody Comment comment, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (comment.getText().isEmpty() || comment.getText().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        comment.setAuthor(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
        return commentService.create(messageId, comment);
    }

    @GetMapping
    public List<Comment> getAllCommentsByMessageId(@PathVariable Long messageId) {
        return commentService.getAllCommentsByMessageId(messageId);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.delete(id, userDetails);
        return ResponseEntity.ok().build();
    }
}