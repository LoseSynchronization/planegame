package gameresource;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author 失去同步
 */
public class GameUtil {
    //定义常量参数

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final double TRANSPORT_RATE = 0.15;

    public static final int[] PLANE_SPEED = {3, 3, 5, 5, 7, 7, 9};
    public static final int[] BULLET_SPEED = {2, 2, 3, 3, 5, 5, 7};

    public static final int[] GAME_LEVEL = {40, 50, 50, 70, 80, 100, 130};

    public static final double[] TIME_LEVEL = {20, 20, 15, 15, 10, 10, 10};

    //资源文件路径

    public static Image[] PLANE_BOMB_IMAGES;

    public static final String BG_IMAGE_PATH = "gameresource/images/bg.jpg";
    public static final String BULLET_IMAGE_PATH = "gameresource/images/bullet.png";
    public static final String PLANE_IMAGE_PATH = "gameresource/images/plane.png";
    public static final String BOME_IMAGE_PATH = "gameresource/images/explode/";

    public static final String BG_MUSIC_PATH = "gameresource/sound/bgmusic.wav";
    public static final String BOME_MUSIC_PATH = "gameresource/sound/boom.wav";
    public static final String LEVEILUP_MUSIC_PATH = "gameresource/sound/levelUp.wav";

    //加载图片
    static{
        PLANE_BOMB_IMAGES = new Image[16];
        for (int i = 0; i < 16; i++) {
            PLANE_BOMB_IMAGES[i] = getImage(BOME_IMAGE_PATH+"e"+(i+1)+".gif");
        }
    }

    //加载图片的方法
    public static Image getImage(String imagePath) {
        BufferedImage image = null;
        URL u = GameUtil.class.getClassLoader().getResource(imagePath);
        try {
            image = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
