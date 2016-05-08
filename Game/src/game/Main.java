package game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	private GameWindow window;
	
	private Level level;
	
	private ArrayList<Sprite> sprites;
	

	Random r = new Random();
	
	public Main() {
		window = new GameWindow(1280, 720);
		try {
			Tilemap levelTilemap = new Tilemap("tilemap3.png", 32, 32, 32);
			
			int levelSize = 128;
			int[][] tiles = new int[levelSize][levelSize];
			boolean[][] walkable = new boolean[levelSize][levelSize];
			for(int x = 0; x < levelSize; x++){
				for(int y = 0; y < levelSize; y++){
					tiles[x][y] = r.nextInt(1000);
					walkable[x][y] = r.nextInt(5) == 0;
				}
			}
			
			level = new Level(levelTilemap, tiles, walkable);
			
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
			
			level.draw();
			
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