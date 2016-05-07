package game;

import java.awt.Color;
import java.awt.Graphics;

public class Main {
	
	private GameWindow window;
	
	
	public Main() {
		window = new GameWindow();
	}

	private void gameloop() {
		while(true){
			
			Graphics g = window.getGraphics();
			
			g.setColor(Color.red);
			g.fillRect(0, 0, 1024, 768);
			
			window.swapBuffers();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Entry point of the program.
	 * @param args console arguments
	 */
	public static void main(String[] args) {
		new Main().gameloop();
	}
}