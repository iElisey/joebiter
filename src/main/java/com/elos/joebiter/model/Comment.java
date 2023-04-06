package com.elos.joebiter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("messages") // Ignore comments property to avoid infinite recursion
    private User author;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    @JsonIgnoreProperties("comments") // Ignore comments property to avoid infinite recursion
    private Message message;
}
