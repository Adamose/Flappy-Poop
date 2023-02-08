package code;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

//Class for pipe instances
public class Pipe {

	private Random random;
	private Color myBrown;
	private int type;
	private int velocity;
	private int width;
	private int x;
	private int y1;
	private int height1;
	private int y2;
	private int height2;

	public Pipe() {

		//Initializing variables
		random = new Random();
		myBrown = new Color(139, 69, 19);
		type = random.nextInt(7) + 1;
		velocity = 4;
		width = 75;
		x = 500;
		y1 = 0;
		y2 = 0;

		//Setting pipe's dimensions based on randomly selected type
		switch (type) {
			case 1:
				height1 = 175;
				height2 = 175;
				y2 = 325;
				break;
			case 2:
				height1 = 100;
				height2 = 250;
				y2 = 250;
				break;
			case 3:
				height1 = 250;
				height2 = 100;
				y2 = 400;
				break;
			case 4:
				height1 = 225;
				height2 = 125;
				y2 = 375;
				break;
			case 5:
				height1 = 125;
				height2 = 225;
				y2 = 275;
				break;
			case 6:
				height1 = 25;
				height2 = 325;
				y2 = 175;
				break;
			case 7:
				height1 = 300;
				height2 = 50;
				y2 = 450;
				break;
		}
	}

	//Method to paint pipe's two rectangles
	public void paint(Graphics g) {
		g.setColor(myBrown);
		g.fillRect(x, y1, width, height1);
		g.fillRect(x, y2, width, height2);
	}

	//Method to update pipes location (move it to the left)
	public void update() {
		x -= velocity;
	}

	public int getX() {
		return this.x;
	}

	public int getY2() {
		return this.y2;
	}

	public int getHeight1() {
		return this.height1;
	}

	public int getHeight2() {
		return this.height2;
	}

	public int getWidth() {
		return this.width;
	}
}