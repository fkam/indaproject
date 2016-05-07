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
			tilemap = new Tilemap("tilemap2.png", 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void gameloop() {
		while(true){
			
			Graphics g = window.getGraphics();
			
			g.setColor(Color.red);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			tilemap.drawImage(g, 0, 0, 3);
			
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