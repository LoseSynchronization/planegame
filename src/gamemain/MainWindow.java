package gamemain;

import gameobject.Bullet;
import gameobject.Plane;
import gameresource.Explode;
import gameresource.GameUtil;
import gameresource.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * 此处用于显示游戏主窗口
 *
 * @author 失去同步
 */
public class MainWindow extends JFrame {

    private double leftTime;
    private int gameLevel;
    private Date beginTime, currentTime;
    private Image bg;
    private Plane player;
    private ArrayList<Bullet> bullets;
    private Explode explode;
    private Sound bgMusic;
    private Sound levelUpMusic;
    private boolean isLevelUp = false;
    public static final int FPS = 30;

    public void launchGame() {

        //设置基本参数
        setTitle("PlaneGame");
        setSize(GameUtil.WIDTH, GameUtil.HEIGHT);
        setLocation(200, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                player.minusDirection(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !player.isAlive()){
                    gameLevel = 0;
                    beginTime = new Date();
                    isLevelUp = false;
                    gameInit();
                    player.reborn();
                }
                player.addDirection(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        //游戏初始化
        gameLevel = 0;
        bg = GameUtil.getImage(GameUtil.BG_IMAGE_PATH);
        levelUpMusic = new Sound(GameUtil.LEVEILUP_MUSIC_PATH);
        bgMusic = new Sound(GameUtil.BG_MUSIC_PATH);
        gameInit();
        setVisible(true);
        bufferImage();

        //暂停，给玩家准备时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        //启动线程
        new GameThread().start();

    }

    //游戏初始化
    private void gameInit() {
        leftTime = GameUtil.TIME_LEVEL[gameLevel];
        player = new Plane(GameUtil.PLANE_IMAGE_PATH, GameUtil.PLANE_SPEED[gameLevel], 100, 100);
        bullets = new ArrayList<>(GameUtil.GAME_LEVEL[gameLevel]);
        for (int i = 0; i < GameUtil.GAME_LEVEL[gameLevel]; i++) {
            int bulletx = 20 + new Random().nextInt(GameUtil.WIDTH - 20);
            int bullety = 20 + new Random().nextInt(GameUtil.HEIGHT - 20);
            Bullet bullet = new Bullet(GameUtil.BULLET_IMAGE_PATH, GameUtil.BULLET_SPEED[gameLevel], bulletx, bullety);
            //初始子弹与飞机有一定距离
            while (bulletx < 150 && bullety < 170) {
                bulletx = 20 + new Random().nextInt(GameUtil.WIDTH - 20);
                bullety = 20 + new Random().nextInt(GameUtil.HEIGHT - 20);
                bullet.setLocation(bulletx, bullety);
            }
            bullets.add(bullet);
        }
        beginTime = new Date();
        bgMusic.start();
    }

    private void gameOver(Graphics gr) {
        bgMusic.stop();
        paintString("Game Over", 270, 250, 50, Color.RED, gr);
        paintString("Current : Level:" + (gameLevel + 1), 320, 300, 20, Color.WHITE, gr);
        paintString("press SPACE to restart", 270, 430, 25, Color.RED, gr);
    }

    private void levelUp(Graphics gr) {
        try {
            System.out.println("level up");
            bgMusic.stop();
            levelUpMusic.start();
            if (gameLevel < GameUtil.GAME_LEVEL.length - 1){
                gameLevel++;
            }
            paintLevelUp(gr);
            //暂停3s
            Thread.sleep(3000);
            levelUpMusic.stop();
            gameInit();//初始化游戏
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void paintLevelUp(Graphics gr) {
        paintString("Level Up !", 270, 250, 50, Color.RED, gr);
        paintString("Current : Level:" + (gameLevel + 1), 300, 300, 20, Color.WHITE, gr);
    }

    @Override
    public void paint(Graphics gr) {

        paintInfo(gr);
        //飞机打印
        if (player.isAlive()){
            if (isLevelUp){
                isLevelUp = false;
                levelUp(gr);
            } else {
                player.draw(gr);
                player.move();
                currentTime = new Date();
                leftTime = GameUtil.TIME_LEVEL[gameLevel] - (currentTime.getTime() - beginTime.getTime()) / 1000.0;
                if (leftTime <= 0){
                    isLevelUp = true;
                }
            }
        } else {
            explode.draw(gr);
            gameOver(gr);
        }

        //子弹打印及碰撞检测
        Bullet[] bs = bullets.toArray(new Bullet[0]);
        for (int i = 0; i < bs.length; i++) {
            Bullet b = bs[i];
            b.draw(gr);
            b.move();
            //检测是否相撞
            if (player.geRectangle().intersects(b.geRectangle()) && player.isAlive()){
                bgMusic.stop();
                explode = player.die();
            }
        }
    }

    //打印文本
    private void paintString(String str, int x, int y, int size, Color color, Graphics gr) {
        Color c = gr.getColor();
        gr.setColor(color);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, size);
        gr.setFont(font);
        gr.drawString(str, x, y);
        gr.setColor(c);
    }

    //打印游戏信息
    private void paintInfo(Graphics gr) {
        paintString("Level: " + (gameLevel + 1), 50, 50, 20, Color.WHITE, gr);
        paintString(String.format("Left Time: %.2f s", leftTime < 0 ? 0 : leftTime),
                GameUtil.WIDTH - 200, 50, 20, Color.GREEN, gr);
        paintString("Keep Alive! ", GameUtil.WIDTH - 200, 70, 20, Color.green, gr);
        paintString("Tip:using WSAD or Up,Down,Left,Right to Control plane", 5, getHeight() - 10, 12, Color.yellow, gr);
    }

    @Override
    public void repaint() {
        bufferImage();
        update(getGraphics());
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    private Image image;
    private Graphics graphics;

    //缓冲
    private void bufferImage() {
        if (image == null || graphics == null){
            image = this.createImage(getWidth(), getHeight());
            graphics = image.getGraphics();
        }
        graphics.drawImage(bg, 0, 0, GameUtil.WIDTH, GameUtil.HEIGHT, null);
        paint(graphics);
        this.getGraphics().drawImage(image, 0, 0, null);
    }

    class GameThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        MainWindow gw = new MainWindow();
        gw.launchGame();
    }

}
