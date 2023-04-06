package com.elos.joebiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDto {
    private Long id;
    private String username;

    public SubscriberDto generate(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        return this;
    }

}
