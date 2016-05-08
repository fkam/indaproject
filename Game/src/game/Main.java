package game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class Main {
	
	private GameWindow window;
	private Tilemap tilemap;
	
	public Main() {
		window = new GameWindow(1280, 720);
		try {
			tilemap = new Tilemap("tilemap3.png", 32, 32, 32);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gameloop() {
		while(true){
			
			Graphics g = window.getGraphics();
			
			g.setColor(Color.white);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			tilemap.drawImage(g, 0, 0, 128);
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