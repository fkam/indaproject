package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	
	
	private GameWindow window;
	
	private Level level;
	
	private ArrayList<Sprite> sprites;
	
	private Sprite player;
	
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
					tiles[x][y] = 100+r.nextInt(100);
					walkable[x][y] = r.nextInt(10) != 0;
					if(!walkable[x][y]){
						tiles[x][y] = 32;
					}
				}
			}
			
			level = new Level(levelTilemap, tiles, walkable);
			
			Tilemap spriteSheet = new Tilemap("spritesheet1.png", 12, 33, 35);
			
			sprites = new ArrayList<>();
			
			player = new Sprite(spriteSheet, 0, levelSize/2, levelSize/2);
			sprites.add(player); 
			
			for(int i = 0; i < 1440; i++){
				sprites.add(new NPC(spriteSheet, r.nextInt(4) * 3, r.nextInt(levelSize), r.nextInt(levelSize)));
				//sprites.add(new NPC(spriteSheet, r.nextInt(4) * 3, 64, 64));
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


			g.translate(window.getWidth()/2 - player.getX(), window.getHeight()/2 - player.getY());
			
			level.draw(g);
			
			if(!player.isWalking()){
				if(window.isKeyDown(KeyEvent.VK_UP)){
					player.walk(level, sprites, Sprite.DIRECTION_UP);
				}
				else if(window.isKeyDown(KeyEvent.VK_LEFT)){
					player.walk(level, sprites, Sprite.DIRECTION_LEFT);
				}
				else if(window.isKeyDown(KeyEvent.VK_RIGHT)){
					player.walk(level, sprites, Sprite.DIRECTION_RIGHT);
				}
				else if(window.isKeyDown(KeyEvent.VK_DOWN)){
					player.walk(level, sprites, Sprite.DIRECTION_DOWN);
				}
			}
			
			for(int i = 0; i < sprites.size(); i++){
				
				Sprite sprite = sprites.get(i);
				sprite.update(level, sprites);
				sprite.draw(g);
				
			}
			
			
			window.swapBuffers();
			
			
			try {
				Thread.sleep(0);
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