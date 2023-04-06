package com.elos.joebiter.controller;

@RestController
public class DrawImage {
    @GetMapping("/image")
    public ByteArrayResource getImage() throws IOException {
        CustomImage customImage = new CustomImage(200, 100);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(customImage.getImage(), "png", baos);
        return new ByteArrayResource(baos.toByteArray());
    }
}
