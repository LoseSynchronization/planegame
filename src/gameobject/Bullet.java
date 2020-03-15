package gameobject;

import java.awt.Image;

import gameresource.GameUtil;

public class Bullet extends GameObject {

    private double degree;

    public Bullet(Image image, int speed, int x, int y) {
        super(image, speed, x, y);
        degree = Math.random() * Math.PI * 2;
    }

    public Bullet(String imagePath, int speed, int x, int y) {
        super(imagePath, speed, x, y);
        degree = Math.random() * Math.PI * 2;
    }

    public void move() {
        boolean isOutOfBound = false;
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        if (x < 0 || x > GameUtil.WIDTH - width){
            degree = Math.PI - degree;
            isOutOfBound = true;
        }
        if (y < 0 || y > GameUtil.HEIGHT - height){
            degree = -degree;
            isOutOfBound = true;
        }
        if (isOutOfBound){
            randomTransport();
        }

    }

    private void randomTransport() {
        if (Math.random() <= GameUtil.TRANSPORT_RATE){
            degree = Math.random() * Math.PI * 2;
            setLocation(GameUtil.WIDTH / 2, GameUtil.HEIGHT / 2);
        }
    }
}
