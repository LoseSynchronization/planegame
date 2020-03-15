package gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import gameresource.GameUtil;

public class GameObject {
	
	double x,y;//坐标
	Image image;//图片
	int width,height;//宽和高
	int speed;//速度
	
	
	private GameObject(int speed,int x,int y,int width,int height) {
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public GameObject(Image image,int speed,int x,int y) {
		this(speed,x,y,image.getWidth(null),image.getHeight(null));
		this.image = image;
	}
	
	public GameObject(String imagePath, int speed, int x, int y) {
		this(GameUtil.getImage(imagePath), speed, x, y);
	}
	
	public void draw(Graphics gr) {
		gr.drawImage(image, (int)x, (int)y, null);
	}
	
	public Rectangle geRectangle() {
		return new Rectangle( (int)x, (int)y, width-5, height-5);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setLocation(int x, int y){
		setX(x);
		setY(y);
	}
}
