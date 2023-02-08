package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

//Class for game logic and main game loop
public class Panel extends JPanel {

	Poop poop;
	Background background;
	ArrayList<Pipe> pipes;
	boolean inGame;
	boolean isAlive;
	boolean inMenu;
	boolean firstLoad;
	int score;
	int highscore;
	Font font1;
	Font font2;
	Font font3;
	Font font4;
	Color myBlue1;
	Color myBlue2;
	Clip fart;
	Clip ding;

	//Timer for main game loop (runs at 60hz to get 60 fps)
	Timer timer = new Timer(15, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			update();
		}
	});

	//Timer to create a new pipe instance (adds a new pipe every 1.4 seconds)
	Timer timer2 = new Timer(1400, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			pipes.add(new Pipe());
		}
	});

	public Panel() {
		this.setPreferredSize(new Dimension(500, 500));

		//Initializing variables
		poop = new Poop(40);
		background = new Background();
		pipes = new ArrayList<Pipe>();
		inGame = false;
		isAlive = false;
		inMenu = true;
		firstLoad = true;
		score = 0;
		highscore = 0;
		font1 = new Font("Impact", Font.BOLD, 100);
		font2 = new Font("ArialBlack", Font.PLAIN, 40);
		font3 = new Font("ComicSansMS", Font.PLAIN, 35);
		font4 = new Font("Impact", Font.BOLD, 50);
		myBlue1 = new Color(0, 213, 255);
		myBlue2 = new Color(0, 0, 201);

		//Loading sounds
		try {
			URL url1 = Main.class.getResource("/resouces/ding.wav");
			AudioInputStream audio1 = AudioSystem.getAudioInputStream(url1);
			ding = AudioSystem.getClip();
			ding.open(audio1);

			URL url2 = Main.class.getResource("/resouces/fart.wav");
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(url2);
			fart = AudioSystem.getClip();
			fart.open(audio2);
		} catch (Exception e) {
			System.out.println("Failed to load sounds");
			System.exit(-1);
		}

		//Event handler for SPACE key presses
		InputMap i = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "space");
		ActionMap a = this.getActionMap();
		a.put("space", new AbstractAction() {

			//Make poop jump when user presses SPACE
			public void actionPerformed(ActionEvent e) {
				if (isAlive) {
					poop.up();
				}
			}
		});

		//Event handler for ENTER key presses
		InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "enter");
		ActionMap am = this.getActionMap();
		am.put("enter", new AbstractAction() {

			//Start a round when player presses enter
			public void actionPerformed(ActionEvent e) {
				if (inMenu) {
					firstLoad = false;
					score = 0;
					inGame = true;
					isAlive = true;
					inMenu = false;
					poop.setDead(false);
					timer2.start();
					pipes.add(new Pipe());
				}
			}
		});

		//Starting game loop
		timer.start();
	}

	//Method to paint all game components
	public void paint(Graphics g) {

		//Checking if user is inGame, if not paint menu
		if (inGame) {

			//Painting game components
			background.paint(g);
			for (Pipe p : pipes) { p.paint(g); }
			poop.paint(g);

			//Painting score
			g.setFont(font4);
			g.setColor(Color.BLACK);
			g.drawString("" + score, 250, 50);
			
		} else {
			
			//Switching to a graphics2D context to get smoother text
			Graphics2D g2 = (Graphics2D) g;
        	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			//Painting background color
			g2.setColor(myBlue1);
			g2.fillRect(0, 0, 500, 500);

			//Painting title
			g2.setFont(font1);
			g2.setColor(myBlue2);
			g2.drawString("FLAPPY", 110, 150);
			g2.drawString("poop", 150, 225);

			//Painting scores
			g2.setFont(font2);
			g2.setColor(Color.blue);
			g2.drawString("HighScore:" + highscore, 145, 315);
			g2.drawString("Score:" + score, 190, 360);

			//Painting messafe
			g2.setFont(font3);
			g2.setColor(Color.black);
			g2.drawString("Press ENTER To Play", 90, 415);

			//If first time in menu, paint keybind
			if (firstLoad) {
				g2.drawString("Press Space To Jump", 90, 465);
			}
		}
	}

	//Method to update game components and check if player gained a point or lost the round
	public void update() {

		//Checking if player is in game (no need to update components while in menu)
		if (inGame) {

			//Updating poop
			poop.update();

			//Checking if player is alive (round is still running)
			if (isAlive) {

				//Removing pipes that are off screen
				pipes.removeIf(pipe -> pipe.getX() < -80);

				//Updating background
				background.update();

				//Updating all pipes
				for (Pipe p : pipes) {
					p.update();

					//Checking to see if poop collided with lower part of pipe
					if (new Rectangle(p.getX(), 0, p.getWidth(), p.getHeight1()).intersects(new Rectangle(poop.getX(), poop.getY(), poop.getSize(), poop.getSize()))

						//Checking to see if poop collided with top part of pipe
						|| new Rectangle(p.getX(), p.getY2(), p.getWidth(), p.getHeight2()).intersects(new Rectangle(poop.getX(), poop.getY(), poop.getSize(), poop.getSize()))) {
		
						isAlive = false;
						timer2.stop();
						fart.setFramePosition(8000);
						fart.start();
						poop.setDead(true);
					}

					//Checking if pipe passed by player without getting touched
					if (p.getX() + 75 == 127) {
						ding.setFramePosition(18000);
						ding.start();
						score += 1;
					}
				}
			}

			//Checking if poop hit the floor
			if (poop.getY() >= 450) {

				//Check if player was alive (to prevent from playing fart sound twice)
				if (isAlive == true) {
					isAlive = false;
					timer2.stop();
					fart.setFramePosition(8000);
					fart.start();
					poop.setDead(true);
				}

				//Check if highscore needs to be updated
				if (highscore < score) {
					highscore = score;
				}

				inGame = false;
				pipes.clear();
				poop.setY(200);
				inMenu = true;
			}
		}

		//Updating screen
		repaint();
	}
}