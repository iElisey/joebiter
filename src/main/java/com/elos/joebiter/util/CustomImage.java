package com.elos.joebiter.util;

import com.elos.joebiter.model.Message;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

public class CustomImage extends JPanel {

    private BufferedImage image;

    public CustomImage(List<Message> messages) {

        int width = 500;
        int height = messages.size() == 0 ? 200 : 25 * messages.size();
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        int y = 25;

        for (int j = 0; j < messages.size(); j++) {
            Color color = getRandomColor();
            g.setColor(color);
            Message message = messages.get(j);
            g.setFont(Font.getFont("Arial"));
            String text = message.getText();
            String author = message.getAuthor().getUsername();
            g.drawString(text, 25, y);
            g.drawString(author, 150 + text.length() + 40, y);
//            g.drawString(j+1+"."+ text +" - "+ author, 1, j*15);
            y += 25;
        }

    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}