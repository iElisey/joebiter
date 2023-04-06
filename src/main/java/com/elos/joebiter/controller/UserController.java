package com.elos.joebiter.controller;

import com.elos.joebiter.model.User;
import com.elos.joebiter.repository.UserRepository;
import com.elos.joebiter.service.UserDetailsImpl;
import com.elos.joebiter.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{id}")
    public User get(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow();
    }


    @PutMapping("/changeSubscribe/{subscribeId}")
    public ResponseEntity<?> changeSubscribeStatus(
            @PathVariable Long subscribeId,
            @AuthenticationPrincipal UserDetailsImpl detailsSubscriber
    ) {
        if (detailsSubscriber == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            User subscriber = userRepository.findById(detailsSubscriber.getId()).get();
            User subscribe = userRepository.findById(subscribeId).orElseThrow();

            boolean isSubscribed = subscriber.getSubscriptions().contains(subscribe);

            if (isSubscribed) {
                subscriber.getSubscriptions().remove(subscribe);
            } else {
                subscriber.getSubscriptions().add(subscribe);
            }

            userRepository.save(subscriber);
            return ResponseEntity.ok(subscribe.getSubscribers());
        }

    }
}
