package com.elos.joebiter.controller;

import com.elos.joebiter.service.MessageService;
import com.elos.joebiter.util.CustomImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin("*")
public class DrawImageController {

    private final MessageService messageService;

    @Autowired
    public DrawImageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/api/image")
    public ByteArrayResource getImage() throws IOException {
        CustomImage customImage = new CustomImage(new ArrayList<>());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(customImage.getImage(), "png", baos);
        return new ByteArrayResource(baos.toByteArray());
    }
}
