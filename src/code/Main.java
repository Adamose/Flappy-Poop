package code;

import javax.swing.JFrame;

//Class that acts as JFrame and contains program's entry point
public class Main extends JFrame {

	//Constructor set's JFrame's proprities
	public Main() {
		this.setTitle("Flappy Poop");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new Panel());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}