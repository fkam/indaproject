package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	private GameWindow window;
	private Image image;
	
	public Main() {
		window = new GameWindow(1280, 720);
		try {
			image = ImageIO.read(new File("tilemap.png"));
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
			g.drawImage(image, 50, 80, null );
			g.drawImage(image, 0, 0, 128, 64,  null);
			g.drawImage(image, 200, 200, 232, 232, 32, 0, 64, 32, null);
			
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