package game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	private GameWindow window;
	private Tilemap tilemap;
	
	private ArrayList<Sprite> sprites;
	

	Random r = new Random();
	
	public Main() {
		window = new GameWindow(1280, 720);
		try {
			tilemap = new Tilemap("tilemap3.png", 32, 32, 32);
			
			Tilemap spriteSheet = new Tilemap("spritesheet1.png", 12, 33, 35);
			
			sprites = new ArrayList<>();
			for(int i = 0; i < 1000; i++){
				sprites.add(new Sprite(spriteSheet, r.nextInt(4) * 3));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gameloop() {
		while(true){
			
			Graphics g = window.getGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());
			
			for(int i = 0; i < sprites.size(); i++){
				
				Sprite sprite = sprites.get(i);
				
				if(!sprite.isWalking()){
					sprite.walk(r.nextInt(4));
				}
				sprite.update();
				sprite.draw(g);
				
			}
			
			
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