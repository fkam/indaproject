package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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
					tiles[x][y] = 100+r.nextInt(100);
					walkable[x][y] = r.nextInt(5) != 0;
					if(!walkable[x][y]){
						tiles[x][y] = 32;
					}
				}
			}
			
			level = new Level(levelTilemap, tiles, walkable);
			
			Tilemap spriteSheet = new Tilemap("spritesheet1.png", 12, 33, 35);
			
			sprites = new ArrayList<>();
			for(int i = 0; i < 1; i++){
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

			
			Sprite s = sprites.get(0);
			g.translate(window.getWidth()/2 - s.getX(), window.getHeight()/2 - s.getY());
			
			level.draw(g);
			
			for(int i = 0; i < sprites.size(); i++){
				
				Sprite sprite = sprites.get(i);
				
				if(!sprite.isWalking()){
					
					if(window.isKeyDown(KeyEvent.VK_UP)){
						if (level.canWalk(sprite.getTileX(), sprite.getTileY()-1)){
							sprite.walk(Sprite.DIRECTION_UP);
						}
						else{
							sprite.setDirection(Sprite.DIRECTION_UP);
						}
					}else if(window.isKeyDown(KeyEvent.VK_LEFT)){
						if (level.canWalk(sprite.getTileX()-1, sprite.getTileY())){
							sprite.walk(Sprite.DIRECTION_LEFT);
						}
						else{
							sprite.setDirection(Sprite.DIRECTION_LEFT);
						}
					}else if(window.isKeyDown(KeyEvent.VK_RIGHT)){
						if (level.canWalk(sprite.getTileX()+1 , sprite.getTileY())){
							sprite.walk(Sprite.DIRECTION_RIGHT);
						}
						else{
							sprite.setDirection(Sprite.DIRECTION_RIGHT);
						}
					}else if(window.isKeyDown(KeyEvent.VK_DOWN)){
						if (level.canWalk(sprite.getTileX() , sprite.getTileY()+1)){
							sprite.walk(Sprite.DIRECTION_DOWN);
						}
						else{
							sprite.setDirection(Sprite.DIRECTION_DOWN);
						}
					}
					
				}
				sprite.update();
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