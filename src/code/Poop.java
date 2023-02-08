package code;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

//Class for poop instance to be controlled by player
public class Poop {

	private Image alivePoop;
	private Image deadPoop;
	private int velocity;
	private int gravity;
	private int x;
	private int y;
	private int size;
	private int lift;
	public boolean isDead;

	public Poop(int size) {

		//Initializing variables
		this.size = size;
		velocity = 0;
		gravity = 2;
		x = 125;
		y = 200;
		lift = 35;
		isDead = false;

		//Loading images
		URL url1 = Main.class.getResource("/resouces/poop1.png");
		ImageIcon imageicon1 = new ImageIcon(url1);
		Image img1 = imageicon1.getImage();
		alivePoop = img1.getScaledInstance(size + 10, size + 10, Image.SCALE_SMOOTH);

		URL url2 = Main.class.getResource("/resouces/poop2.png");
		ImageIcon imageicon2 = new ImageIcon(url2);
		Image img2 = imageicon2.getImage();
		deadPoop = img2.getScaledInstance(size + 10, size + 10, Image.SCALE_SMOOTH);
	}

	//Method to paint poop (checks wether player is alive or dead)
	public void paint(Graphics g) {
		if (isDead == true) {
			g.drawImage(deadPoop, x, y, null);
		} else {
			g.drawImage(alivePoop, x, y, null);
		}
	}

	//Method call when player presses space (jumps poop up)
	public void up() {
		velocity -= lift;
	}

	//Method used to update poop's location (apply gravity)
	public void update() {

		//Updating location
		velocity += gravity;
		velocity *= 0.87;
		y += velocity;

		//Making sure poop doesn't go off screen
		if (y <= 0) {
			y = 0;
		}
		if (y >= 450) {
			y = 450;
		}
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getSize() {
		return size;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDead(boolean a) {
		isDead = a;
	}
}