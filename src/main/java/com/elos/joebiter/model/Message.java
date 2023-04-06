package com.elos.joebiter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime created;

    private String text;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("messages") // Ignore comments property to avoid infinite recursion
    private User author;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("message") // Ignore message property to avoid infinite recursion
    private List<Comment> comments;
}
