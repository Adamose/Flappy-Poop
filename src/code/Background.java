package code;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

//Class that handles the infinite moving background
public class Background {

	private int width;
	private int height;
	private int velocity;
	private int x;
	private int y;
	private Image background;

	public Background() {

		//Initializing variables
		width = 2547;
		height = 500;
		velocity = 2;
		x = 0;
		y = 0;

		//Loading image
		URL url = Main.class.getResource("/resouces/background.png");
		ImageIcon imageicon = new ImageIcon(url);
		Image img = imageicon.getImage();
		background = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	//Method to paint background (paints two to create infinite effect)
	public void paint(Graphics g) {
		g.drawImage(background, x, y, null);
		g.drawImage(background, x + width, y, null);
	}

	//Method to update background's location
	public void update() {

		//Moving background to the left
		x -= velocity;

		//If background is off the screen bring it back to the left
		if (x <= -2547) {
			x = 0;
		}
	}
}