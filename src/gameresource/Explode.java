package gameresource;

import java.awt.Graphics;
import java.awt.Image;

public class Explode{
	private double x,y;
	private Image[] images;
	private int slowMultiple;//降低爆炸效果播放速度
	private int count;
	private Sound boomSound;
	
	public Explode(Image[] images, double x, double y,String soundPath) {
		this(images, x, y);
		boomSound = new Sound(soundPath);
	}
	
	public Explode(Image[] images, double x, double y,String soundPath,int slowMultiple) {
		this(images, x, y,slowMultiple);
		boomSound = new Sound(soundPath);
	}
	
	public Explode(Image[] images, double x, double y,int slowMultiple) {
		this(images, x, y);
		this.slowMultiple=slowMultiple;
	}
	
	public Explode(Image[] images, double x, double y) {
		this.x = x;
		this.y = y;
		this.images = images;
		count=0;
	}

	public void draw(Graphics gr) {
		if(slowMultiple<=0) {
			slowMultiple=1;
		}
		if(count/slowMultiple<images.length) {
			gr.drawImage(images[count/slowMultiple], (int)x, (int)y, null);
			count++;
		}
	}
	
	public void boom() {
		if(boomSound!=null) {
			boomSound.start();			
		}else {
			System.out.println("no boom sound");
		}
	}
}
