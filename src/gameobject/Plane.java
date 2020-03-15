package gameobject;

import java.awt.Image;
import java.awt.event.KeyEvent;

import gameresource.Explode;
import gameresource.GameUtil;

public class Plane extends GameObject {

    private boolean isLeft, isRight, isUp, isDown;//移动方向控制
    private boolean isAlive = true;

    public Plane(Image image, int speed, int x, int y) {
        super(image, speed, x, y);
    }

    public Plane(String imagePath, int speed, int x, int y) {
        super(imagePath, speed, x, y);
    }

    public void addDirection(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                isUp = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isDown = true;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isLeft = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isRight = true;
                break;
            default:
        }
    }

    public void minusDirection(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                isUp = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isDown = false;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isLeft = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isRight = false;
                break;
            default:
        }
    }

    public void move() {
        if (isLeft && x >= 5){
            x -= speed;
        }
        if (isRight && x <= GameUtil.WIDTH - width){
            x += speed;
        }
        if (isUp && y >= 30){
            y -= speed;
        }
        if (isDown && y <= GameUtil.HEIGHT - height){
            y += speed;
        }
    }

    public Explode die() {
        isAlive = false;
        Explode explode = new Explode(GameUtil.PLANE_BOMB_IMAGES, x, y, GameUtil.BOME_MUSIC_PATH, 3);
        explode.boom();
        return explode;
    }

    public void reborn() {
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

}
