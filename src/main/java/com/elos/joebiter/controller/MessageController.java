package com.elos.joebiter.controller;

import com.elos.joebiter.model.Message;
import com.elos.joebiter.repository.UserRepository;
import com.elos.joebiter.service.MessageService;
import com.elos.joebiter.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    @Autowired
    public MessageController(MessageService messageService,
                             UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Message create(@RequestBody Message message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (message.getText().isEmpty() || message.getText().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        message.setAuthor(userRepository.findByUsername(userDetails.getUsername()).orElseThrow());
        message.setCreated(LocalDateTime.now());
        return messageService.create(message);
    }

    @GetMapping
    public Page<Message> get(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return messageService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    public Message getById(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @GetMapping("/find")
    public Page<Message> findByQuery(@RequestParam("query") String query, @RequestParam(value = "page", required = false) int page, @RequestParam(value = "size", required = false) int size) {
        return messageService.findByQuery(query, PageRequest.of(page, size));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        messageService.delete(id, userDetails);
        return ResponseEntity.ok().build();
    }


//
//    @PutMapping("{id}")
//    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Message message) {
//        messageService.setMessage(id, message);
//        return ResponseEntity.ok().build();
//    }
}
